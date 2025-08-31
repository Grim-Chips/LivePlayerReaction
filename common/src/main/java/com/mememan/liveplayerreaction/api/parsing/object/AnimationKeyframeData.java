package com.mememan.liveplayerreaction.api.parsing.object;

import com.mojang.datafixers.util.Either;
import org.joml.Vector3d;

import java.util.Map;
import java.util.Optional;

public record AnimationKeyframeData(Optional<Map<Double, Either<PrimitiveKeyframeValueData, VerboseKeyframeValueData>>> keyframes) {

    public record PrimitiveKeyframeValueData(Either<String, Double> xKeyframeTarget, Either<String, Double> yKeyframeTarget, Either<String, Double> zKeyframeTarget) {

    }

    public record VerboseKeyframeValueData(Optional<Vector3d> preKeyframeTarget, Optional<Vector3d> postKeyframeTarget) {

    }
}
