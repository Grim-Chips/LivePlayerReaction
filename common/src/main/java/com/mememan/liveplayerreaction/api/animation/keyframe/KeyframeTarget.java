package com.mememan.liveplayerreaction.api.animation.keyframe;

import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mememan.liveplayerreaction.api.math.easings.BedrockEasing;

import java.util.Optional;

public record KeyframeTarget(KeyframeType keyframeType, double targetTick, double xTarget, double yTarget, double zTarget, BedrockEasing easing, Optional<KeyframeTarget> postSnapTarget) {

    public KeyframeTarget(KeyframeType keyframeType, double targetTick, double xTarget, double yTarget, double zTarget, BedrockEasing easing) {
        this(keyframeType, targetTick, xTarget, yTarget, zTarget, easing, Optional.empty());
    }

    public static KeyframeTarget zero() {
        return new KeyframeTarget(null, 0, 0, 0, 0, BedrockEasing.LINEAR);
    }

}
