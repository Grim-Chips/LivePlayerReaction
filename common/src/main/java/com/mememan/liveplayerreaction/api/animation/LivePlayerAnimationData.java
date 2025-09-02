package com.mememan.liveplayerreaction.api.animation;

import com.mememan.liveplayerreaction.api.parsing.object.AnimationCollection;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationData;

import java.util.Optional;

public interface LivePlayerAnimationData {

    Optional<AnimationCollection> getAnimationCollection();

    default Optional<AnimationData> getAnimationData(String animationName) {
        return getAnimationCollection()
                .flatMap(AnimationCollection::animations)
                .map(animMap -> animMap.get(animationName));
    }
}
