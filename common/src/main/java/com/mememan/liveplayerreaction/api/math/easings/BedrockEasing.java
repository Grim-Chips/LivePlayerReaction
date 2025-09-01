package com.mememan.liveplayerreaction.api.math.easings;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

@FunctionalInterface
public interface BedrockEasing {
    Map<String, BedrockEasing> KNOWN_EASINGS = new Object2ObjectOpenHashMap<>();

    // Primary Easing Functions (evaluated without any easing arguments, just the original values)
    BedrockEasing LINEAR = register("linear", BedrockEasing::selectOrDefault);

    double ease(double currentTransitionProgress, OptionalDouble originalPreValue, OptionalDouble originalPostValue); // Double2DoubleFunction

    static BedrockEasing register(String easingName, BedrockEasing easing) {
        KNOWN_EASINGS.put(easingName.toLowerCase(Locale.ROOT), easing); // No #putIfAbsent because overrides ftw (idk it doesn't actually matter all that much here)
        return easing;
    }

    static Optional<BedrockEasing> getEasing(String easingName) {
        return Optional.ofNullable(KNOWN_EASINGS.get(easingName.toLowerCase(Locale.ROOT)));
    }

    static Optional<String> getNameForEasing(BedrockEasing easing) {
        return KNOWN_EASINGS.entrySet().stream()
            .filter(entry -> entry.getValue().equals(easing))
            .map(Map.Entry::getKey)
            .findFirst();
    }

    static double selectOrDefault(double currentTransitionProgress, OptionalDouble originalPreValue, OptionalDouble originalPostValue) {
        return originalPreValue.orElse(originalPostValue.orElse(0)); // Fallback should never be hit, but JIC
    }
}
