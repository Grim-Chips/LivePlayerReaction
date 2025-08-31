package com.mememan.liveplayerreaction.api.parsing.object;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;
import java.util.Optional;

public record AnimationCollection(Optional<Map<String, AnimationData>> animations) {
    public static final Codec<AnimationCollection> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, AnimationData.CODEC).optionalFieldOf("animations").forGetter(AnimationCollection::animations)
    ).apply(instance, AnimationCollection::new));
}
