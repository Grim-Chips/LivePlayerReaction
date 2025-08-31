package com.mememan.liveplayerreaction.api.parsing.object;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;
import java.util.Optional;

public record AnimationData(Optional<Double> animLength, Optional<Double> blendWeight, Optional<Double> animTimeUpdate, Optional<Boolean> overridePrevAnim, Optional<Boolean> loop, Optional<Map<String, AnimationBoneKeyframeData>> bones) {
    public static final Codec<AnimationData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.optionalFieldOf("animation_length").forGetter(AnimationData::animLength),
            Codec.DOUBLE.optionalFieldOf("blend_weight").forGetter(AnimationData::blendWeight),
            Codec.DOUBLE.optionalFieldOf("anim_time_update").forGetter(AnimationData::animTimeUpdate),
            Codec.BOOL.optionalFieldOf("override_previous_animation").forGetter(AnimationData::overridePrevAnim),
            Codec.BOOL.optionalFieldOf("loop").forGetter(AnimationData::loop),
            Codec.unboundedMap(Codec.STRING, AnimationBoneKeyframeData.CODEC).optionalFieldOf("bones").forGetter(AnimationData::bones)
    ).apply(instance, AnimationData::new));
}
