package com.mememan.liveplayerreaction.api.animation.keyframe;

import java.util.List;
import java.util.Map;

public record KeyframeTargetStack(Map<String, List<KeyframeTarget>> organizedTargets) {

    public static KeyframeTargetStack zero() {
        return new KeyframeTargetStack(Map.of());
    }
}
