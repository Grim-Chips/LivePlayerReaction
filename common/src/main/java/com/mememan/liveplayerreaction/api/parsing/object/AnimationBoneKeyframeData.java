package com.mememan.liveplayerreaction.api.parsing.object;

import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record AnimationBoneKeyframeData(Optional<Map<KeyframeType, Either<List<Either<String, Double>>, AnimationKeyframeData>>> mappedKeyframeData) {
    public static final Codec<AnimationBoneKeyframeData> CODEC = Codec.unboundedMap(KeyframeType.CODEC, Codec.either(Codec.list(Codec.either(Codec.STRING, Codec.DOUBLE)), AnimationKeyframeData.CODEC)).xmap(
            keyframeMap -> new AnimationBoneKeyframeData(Optional.ofNullable(keyframeMap)),
            animationBoneKeyframeData -> animationBoneKeyframeData.mappedKeyframeData().orElse(Map.of())
    );
}
