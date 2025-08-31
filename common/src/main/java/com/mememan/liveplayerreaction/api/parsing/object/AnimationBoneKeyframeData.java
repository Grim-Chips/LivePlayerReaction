package com.mememan.liveplayerreaction.api.parsing.object;

import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;

import java.util.Map;
import java.util.Optional;

public record AnimationBoneKeyframeData(Optional<Map<KeyframeType, AnimationKeyframeData>> mappedKeyframeData) {

}
