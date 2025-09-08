package com.mememan.liveplayerreaction.api.parsing.object;

import com.mememan.liveplayerreaction.LPRConstants;
import com.mememan.liveplayerreaction.api.math.easings.BedrockEasing;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import team.unnamed.mocha.runtime.MochaFunction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public record AnimationKeyframeData(Optional<Map<Double, Either<PrimitiveKeyframeValueData, VerboseKeyframeValueData>>> keyframes) {
    public static final Codec<Either<String, Double>> KEYFRAME_INFO_CODEC = Codec.either(Codec.STRING, Codec.DOUBLE);
    public static final Codec<List<Either<String, Double>>> KEYFRAME_INFO_ARRAY_CODEC = Codec.list(KEYFRAME_INFO_CODEC);
    public static final Codec<AnimationKeyframeData> CODEC = Codec.unboundedMap(Codec.STRING, Codec.either(PrimitiveKeyframeValueData.CODEC, VerboseKeyframeValueData.CODEC)).xmap(
            keyframeMap -> new AnimationKeyframeData(Optional.of(keyframeMap.entrySet().stream()
                    .collect(Collectors.toMap(curEntry -> Double.parseDouble(curEntry.getKey()), Map.Entry::getValue, (a, b) -> a, Object2ObjectOpenHashMap::new)))),
            animationKeyframeData -> animationKeyframeData.keyframes().map(curMap -> curMap.entrySet().stream()
                    .collect(Collectors.toMap(curEntry -> String.valueOf(curEntry.getKey()), Map.Entry::getValue, (a, b) -> a, Object2ObjectOpenHashMap::new))).orElse(new Object2ObjectOpenHashMap<>())
    );
    private static final Object2ObjectOpenHashMap<String, MochaFunction> CACHED_EXPRESSION_LOOKUP = new Object2ObjectOpenHashMap<>();

    public static Optional<Double> pickValue(Either<String, Double> potentialValue) {
        if (potentialValue == null) return Optional.empty();

        AtomicReference<Double> pickedValue = new AtomicReference<>(); // Allow for nullability

        potentialValue
                .ifLeft(molangExpression -> pickedValue.set(CACHED_EXPRESSION_LOOKUP.computeIfAbsent(molangExpression, (String me) -> LPRConstants.MOLANG_EVALUATOR.prepareEval(me)).evaluate()))
                .ifRight(pickedValue::set);

        return Optional.ofNullable(pickedValue.get());
    }

    public record PrimitiveKeyframeValueData(Either<String, Double> xKeyframeTarget, Either<String, Double> yKeyframeTarget, Either<String, Double> zKeyframeTarget) {
        public static final Codec<PrimitiveKeyframeValueData> CODEC = KEYFRAME_INFO_ARRAY_CODEC.xmap(
                keyframeTargets -> new PrimitiveKeyframeValueData(keyframeTargets.get(0), keyframeTargets.get(1), keyframeTargets.get(2)),
                primitiveKeyframeValueData -> ObjectArrayList.of(primitiveKeyframeValueData.xKeyframeTarget(), primitiveKeyframeValueData.yKeyframeTarget(), primitiveKeyframeValueData.zKeyframeTarget())
        );
    }

    public record VerboseKeyframeValueData(Optional<List<Either<String, Double>>> preKeyframeTarget, Optional<List<Either<String, Double>>> postKeyframeTarget, Optional<BedrockEasing> lerpMode) {
        public static final Codec<BedrockEasing> EASING_CODEC = Codec.STRING.xmap(
                easingName -> BedrockEasing.getEasing(easingName).orElse(BedrockEasing.LINEAR),
                easingType -> BedrockEasing.getNameForEasing(easingType).orElse("linear")
        );
        public static final Codec<VerboseKeyframeValueData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                KEYFRAME_INFO_ARRAY_CODEC.optionalFieldOf("pre").forGetter(VerboseKeyframeValueData::preKeyframeTarget),
                KEYFRAME_INFO_ARRAY_CODEC.optionalFieldOf("post").forGetter(VerboseKeyframeValueData::postKeyframeTarget),
                EASING_CODEC.optionalFieldOf("lerp_mode").forGetter(VerboseKeyframeValueData::lerpMode)
        ).apply(instance, VerboseKeyframeValueData::new));
    }
}
