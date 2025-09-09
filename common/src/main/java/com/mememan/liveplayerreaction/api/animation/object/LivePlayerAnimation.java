package com.mememan.liveplayerreaction.api.animation.object;

import com.mememan.liveplayerreaction.api.animation.LPRAnimations;
import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTarget;
import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTargetStack;
import com.mememan.liveplayerreaction.api.math.easings.BedrockEasing;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationBoneKeyframeData;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationKeyframeData;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LivePlayerAnimation implements LiveAnimation {
    @NotNull
    protected final ConfigurableAnimation animConfig;
    @NotNull
    protected final KeyframeTargetStack targetStack;

    public LivePlayerAnimation(@NotNull ConfigurableAnimation animConfig) {
        this.animConfig = animConfig;
        this.targetStack = LPRAnimations.getAnimationData(animConfig.getAnimationName()).orElseThrow(() -> new IllegalArgumentException(String.format("Animation '%s' does not exist", animConfig.getAnimationName())))
                .bones()
                .filter(curMap -> !curMap.isEmpty())
                .map(curMap -> new KeyframeTargetStack(curMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> {
                                    AnimationBoneKeyframeData boneData = entry.getValue();

                                    return boneData.mappedKeyframeData().map(curBoneKFMap -> {
                                        List<KeyframeTarget> keyframeTargets = new ObjectArrayList<>();

                                        curBoneKFMap.forEach((kfType, broadKFTarget) -> {
                                            Optional<List<Either<String, Double>>> primitiveTarget = broadKFTarget.left(); // Doing this instead of #ifLeft/#ifRight since I originally wanted to implement an Either capable of holding both
                                            Optional<AnimationKeyframeData> nestedAnimationTarget = broadKFTarget.right();

                                            primitiveTarget.ifPresentOrElse(primTargets -> {
                                                DoubleList mappedTarget = primTargets.stream()
                                                        .map(AnimationKeyframeData::pickValue)
                                                        .filter(Optional::isPresent)
                                                        .map(Optional::get)
                                                        .collect(Collectors.toCollection(DoubleArrayList::new));

                                                if (mappedTarget.size() >= 3) {
                                                    keyframeTargets.add(new KeyframeTarget(
                                                            kfType,
                                                            0,
                                                            mappedTarget.getDouble(0),
                                                            mappedTarget.getDouble(1),
                                                            mappedTarget.getDouble(2),
                                                            BedrockEasing.LINEAR));
                                                }
                                            }, () -> nestedAnimationTarget.ifPresent(akfData -> {
                                                Optional<Map<Double, Either<AnimationKeyframeData.PrimitiveKeyframeValueData, AnimationKeyframeData.VerboseKeyframeValueData>>> mappedKeyframes = akfData.keyframes();

                                                mappedKeyframes.ifPresent(keyframes -> {
                                                    if (!keyframes.isEmpty()) {
                                                        keyframes.forEach((curTime, verboseTarget) -> {
                                                            Optional<AnimationKeyframeData.PrimitiveKeyframeValueData> primTarget = verboseTarget.left();
                                                            Optional<AnimationKeyframeData.VerboseKeyframeValueData> verbTarget = verboseTarget.right();
                                                            double curTick = curTime * 20.0D;

                                                            primTarget.ifPresentOrElse(presentPrimTarget -> {
                                                                Optional<Double> primXTarget = AnimationKeyframeData.pickValue(presentPrimTarget.xKeyframeTarget());
                                                                Optional<Double> primYTarget = AnimationKeyframeData.pickValue(presentPrimTarget.yKeyframeTarget());
                                                                Optional<Double> primZTarget = AnimationKeyframeData.pickValue(presentPrimTarget.zKeyframeTarget());

                                                                if (primXTarget.isPresent() && primYTarget.isPresent() && primZTarget.isPresent()) {
                                                                    keyframeTargets.add(new KeyframeTarget(
                                                                            kfType,
                                                                            curTick,
                                                                            primXTarget.get(),
                                                                            primYTarget.get(),
                                                                            primZTarget.get(),
                                                                            BedrockEasing.LINEAR
                                                                    ));
                                                                }
                                                            }, () -> verbTarget.ifPresent(verbPrimTarget -> {
                                                                Optional<List<Either<String, Double>>> preKFTarget = verbPrimTarget.preKeyframeTarget();
                                                                Optional<List<Either<String, Double>>> postKFTarget = verbPrimTarget.postKeyframeTarget();
                                                                BedrockEasing verbEasing = verbPrimTarget.lerpMode().orElse(BedrockEasing.LINEAR);

                                                                preKFTarget.ifPresentOrElse(preKF -> {
                                                                    DoubleList preKFTargets = preKF.stream()
                                                                            .map(AnimationKeyframeData::pickValue)
                                                                            .filter(Optional::isPresent)
                                                                            .map(Optional::get)
                                                                            .collect(Collectors.toCollection(DoubleArrayList::new));

                                                                    if (preKFTargets.size() >= 3) {
                                                                        keyframeTargets.add(new KeyframeTarget(
                                                                                kfType,
                                                                                curTick,
                                                                                preKFTargets.getDouble(0),
                                                                                preKFTargets.getDouble(1),
                                                                                preKFTargets.getDouble(2),
                                                                                verbEasing,
                                                                                postKFTarget
                                                                                        .filter(curList -> curList.size() >= 3)
                                                                                        .map(curList -> {
                                                                                            DoubleList postKFTargets = curList.stream()
                                                                                                    .map(AnimationKeyframeData::pickValue)
                                                                                                    .filter(Optional::isPresent)
                                                                                                    .map(Optional::get)
                                                                                                    .collect(Collectors.toCollection(DoubleArrayList::new));

                                                                                            return new KeyframeTarget(
                                                                                                    kfType,
                                                                                                    curTick,
                                                                                                    postKFTargets.getDouble(0),
                                                                                                    postKFTargets.getDouble(1),
                                                                                                    postKFTargets.getDouble(2),
                                                                                                    verbEasing
                                                                                            );
                                                                                        })
                                                                        ));
                                                                    }
                                                                }, () -> postKFTarget.ifPresent(postKF -> {
                                                                    DoubleList postKFTargets = postKF.stream()
                                                                            .map(AnimationKeyframeData::pickValue)
                                                                            .filter(Optional::isPresent)
                                                                            .map(Optional::get)
                                                                            .collect(Collectors.toCollection(DoubleArrayList::new));

                                                                    if (postKFTargets.size() >= 3) {
                                                                        keyframeTargets.add(new KeyframeTarget(
                                                                                kfType,
                                                                                curTick,
                                                                                postKFTargets.getDouble(0),
                                                                                postKFTargets.getDouble(1),
                                                                                postKFTargets.getDouble(2),
                                                                                verbEasing
                                                                        ));
                                                                    }
                                                                }));
                                                            }));
                                                        });
                                                    }
                                                });
                                            }));
                                        });

                                        return keyframeTargets.stream()
                                                .dropWhile(curKFT -> Objects.equals(curKFT, KeyframeTarget.zero()))
                                                .sorted(Comparator.comparingDouble(KeyframeTarget::targetTick))
                                                .collect(Collectors.toCollection(ObjectArrayList::new));
                                    }).orElseGet(ObjectArrayList::new);
                                },
                                (a, b) -> a,
                                Object2ObjectOpenHashMap::new))))
                .orElseGet(KeyframeTargetStack::zero);
    }

    @Override
    public @NotNull ConfigurableAnimation getAnimationConfig() {
        return animConfig;
    }

    @Override
    public @NotNull KeyframeTargetStack getTargetStack() {
        return targetStack;
    }
}
