package com.mememan.liveplayerreaction.api.animation.transform;

import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.Optional;

public record AnimationTransformationContext(double currentAnimationRenderTick, double contextTickLength, Either<Double, Double> initialTransformValue, double finalTransformValue, Optional<DoubleList> splineControlPoints) {
}
