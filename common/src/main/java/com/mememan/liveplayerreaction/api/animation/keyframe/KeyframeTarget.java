package com.mememan.liveplayerreaction.api.animation.keyframe;

import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mememan.liveplayerreaction.api.math.easings.BedrockEasing;

public record KeyframeTarget(KeyframeType keyframeType, double animRenderTick, double xTarget, double yTarget, double zTarget, BedrockEasing easing) {

    public static KeyframeTarget zero() {
        return new KeyframeTarget(KeyframeType.POSITION, 0, 0, 0, 0, BedrockEasing.LINEAR);
    }

}
