package com.mememan.liveplayerreaction.api.math.easings;

import com.mememan.liveplayerreaction.api.animation.transform.AnimationTransformationContext;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Mth;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@FunctionalInterface
public interface BedrockEasing {
    Map<String, BedrockEasing> KNOWN_EASINGS = new Object2ObjectOpenHashMap<>();

    // Primary Easing Functions (evaluated without any easing arguments besides pre and post, if present, otherwise just the original values)
    BedrockEasing LINEAR = register("linear", BedrockEasing::applyLinearEasing);
    BedrockEasing CATMULLROM = register("catmullrom", BedrockEasing::applyCatmullRomEasing);
    BedrockEasing STEP = register("step", BedrockEasing::applyStepEasing); //TODO Maybe properly implement beyond basic BB functionality if needed
    /* Bezier easings get baked in the final animation file, and the bedrock format does not supply any form of data to hold spline args directly, so no need for it, for now */

    Double2DoubleFunction ease(AnimationTransformationContext transformationContext);

    static double applyEasingTransformation(BedrockEasing easing, AnimationTransformationContext transformationContext) {
        Either<Double, Double> initialTransformValue = transformationContext.initialTransformValue();
        double chosenInitialValue = initialTransformValue
                .left()
                .orElse(initialTransformValue.right().orElseThrow(() -> new IllegalArgumentException(String.format("Initial transform value is missing for '%s' easing at tick: %s", getNameForEasing(easing), transformationContext.currentAnimationRenderTick()))));
        double finalTransformValue = transformationContext.finalTransformValue();
        double curRenderTick = transformationContext.currentAnimationRenderTick();
        double contextTickLength = transformationContext.contextTickLength();

        return curRenderTick >= contextTickLength
                ? finalTransformValue
                : Mth.lerp(chosenInitialValue, finalTransformValue, easing.ease(transformationContext).apply(curRenderTick / contextTickLength));
    }

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

    static Double2DoubleFunction applyLinearEasing(AnimationTransformationContext transformationContext) {
        return (interpolationProgress) -> interpolationProgress;
    }

    static Double2DoubleFunction applyCatmullRomEasing(AnimationTransformationContext transformationContext) {
        return (interpolationProgress) -> {
            Optional<DoubleList> optionalControlPoints = transformationContext.splineControlPoints();

            if (optionalControlPoints.isEmpty() || optionalControlPoints.get().size() < 4) return applyLinearEasing(transformationContext).apply(interpolationProgress);
            else return Mth.catmullrom((float) interpolationProgress, (float) optionalControlPoints.get().getDouble(0), (float) optionalControlPoints.get().getDouble(1), (float) optionalControlPoints.get().getDouble(2), (float) optionalControlPoints.get().getDouble(3));
        };
    }

    static Double2DoubleFunction applyStepEasing(AnimationTransformationContext transformationContext) {
        return (interpolationProgress) -> 1.0F;
    }
}
