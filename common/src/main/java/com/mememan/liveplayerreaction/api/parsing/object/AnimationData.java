package com.mememan.liveplayerreaction.api.parsing.object;

import java.util.Map;
import java.util.Optional;

public record AnimationData(Optional<Double> animLength, Optional<Double> blendWeight, Optional<Double> animTimeUpdate, Optional<Boolean> overridePrevAnim, Optional<Boolean> loop, Optional<Map<String, AnimationBoneKeyframeData>> bones) {

}
