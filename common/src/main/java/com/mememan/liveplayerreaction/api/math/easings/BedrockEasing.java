package com.mememan.liveplayerreaction.api.math.easings;

import com.mememan.liveplayerreaction.api.animation.transform.AnimationTransformationContext;
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

    Double2DoubleFunction ease(AnimationTransformationContext transformationContext);

    static double applyEasingTransformation(BedrockEasing easing, AnimationTransformationContext transformationContext) {
        return easing.ease(transformationContext).apply(transformationContext.currentAnimationRenderTick() / transformationContext.contextTickLength());
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
            else return Mth.catmullrom((float) interpolationProgress, optionalControlPoints.get().get(0).floatValue(), optionalControlPoints.get().get(1).floatValue(), optionalControlPoints.get().get(2).floatValue(), optionalControlPoints.get().get(3).floatValue());
        };
    }
}
