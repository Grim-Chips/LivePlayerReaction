package com.mememan.liveplayerreaction.api.animation.object;

import com.mememan.liveplayerreaction.api.animation.LPRAnimations;
import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTarget;
import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTargetStack;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationBoneKeyframeData;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationKeyframeData;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class LivePlayerAnimation implements LiveAnimation {
    @NotNull
    protected final ConfigurableAnimation animConfig;
    @NotNull
    protected final KeyframeTargetStack targetStack;

    public LivePlayerAnimation(@NotNull ConfigurableAnimation animConfig) {
        this.animConfig = animConfig;
        this.targetStack = LPRAnimations.getAnimationData(animConfig.getAnimationName()).orElseThrow(() -> new IllegalArgumentException(String.format("Animation '%s' does not exist", animConfig.getAnimationName())))
                .bones().map(curMap -> new KeyframeTargetStack(curMap.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> {
                                    AnimationBoneKeyframeData boneData = entry.getValue();

                                    return boneData.mappedKeyframeData().map(curBoneKFMap -> {
                                        AtomicReference<KeyframeTarget> target = new AtomicReference<>(KeyframeTarget.zero());

                                        curBoneKFMap.forEach((kfType, broadKFTarget) -> {
                                            Optional<List<Either<Double, String>>> primitiveTarget = broadKFTarget.left();
                                            Optional<AnimationKeyframeData> nestedAnimationTarget = broadKFTarget.right();

                                            if (primitiveTarget.isPresent()) ;
                                            else if (nestedAnimationTarget.isPresent()) {

                                            }
                                        });

                                        return target.get();
                                    }).orElseGet(KeyframeTarget::zero);
                                },
                                (a, b) -> a,
                                Object2ObjectOpenHashMap::new)))).orElseGet(KeyframeTargetStack::zero);
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
