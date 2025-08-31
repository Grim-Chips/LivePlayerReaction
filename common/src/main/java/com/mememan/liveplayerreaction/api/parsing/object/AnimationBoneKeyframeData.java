package com.mememan.liveplayerreaction.api.parsing.object;

import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mojang.serialization.Codec;

import java.util.Map;
import java.util.Optional;

public record AnimationBoneKeyframeData(Optional<Map<KeyframeType, AnimationKeyframeData>> mappedKeyframeData) {
    public static final Codec<AnimationBoneKeyframeData> CODEC = Codec.unboundedMap(KeyframeType.CODEC, AnimationKeyframeData.CODEC).xmap(
            keyframeMap -> new AnimationBoneKeyframeData(Optional.ofNullable(keyframeMap)),
            animationBoneKeyframeData -> animationBoneKeyframeData.mappedKeyframeData().orElse(Map.of())
    );
}
