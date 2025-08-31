package com.mememan.liveplayerreaction.api.parsing.object;

import java.util.Map;
import java.util.Optional;

public record AnimationCollection(Optional<Map<String, AnimationData>> animations) {

}
