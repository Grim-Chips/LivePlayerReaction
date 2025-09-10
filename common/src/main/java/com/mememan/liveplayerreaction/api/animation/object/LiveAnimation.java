package com.mememan.liveplayerreaction.api.animation.object;

import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTarget;
import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTargetStack;
import com.mememan.liveplayerreaction.api.animation.transform.AnimationTransformationContext;
import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mememan.liveplayerreaction.api.math.easings.BedrockEasing;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LiveAnimation {

    @NotNull
    ConfigurableAnimation getAnimationConfig();

    @NotNull
    KeyframeTargetStack getTargetStack();

    default Optional<Vector3f> getTransformationAt(double renderTickTimeStamp, String boneName, KeyframeType transformationType) {
        KeyframeTargetStack targetStack = getTargetStack();

        if (targetStack == null) return Optional.empty();

        Map<String, List<KeyframeTarget>> targets = targetStack.organizedTargets();

        if (targets.isEmpty()) return Optional.empty();

        List<KeyframeTarget> relevantTargets = targets.getOrDefault(boneName, ObjectArrayList.of());

        if (relevantTargets.isEmpty()) return Optional.empty();

        KeyframeTarget previous = null;
        KeyframeTarget next = null;

        // Find surrounding keyframes
        for (KeyframeTarget target : relevantTargets) {
            if (target.targetTick() == renderTickTimeStamp) return Optional.of(new Vector3f((float) target.xTarget(), (float) target.yTarget(), (float) target.zTarget())); // Exact match found

            if (target.targetTick() < renderTickTimeStamp) previous = target;
            else if (next == null) {
                next = target;
                break; // No need to continue after finding the next keyframe
            }
        }

        // If we're before the first keyframe or after the last keyframe
        if (previous == null || next == null) {
            KeyframeTarget target = previous != null ? previous : next;

            return target != null
                    ? Optional.of(new Vector3f((float) target.xTarget(), (float) target.yTarget(), (float) target.zTarget()))
                    : Optional.empty();
        }

        // Calculate diff
        double timeDiff = next.targetTick() - previous.targetTick();

        if (timeDiff == 0) return Optional.of(new Vector3f((float) previous.xTarget(), (float) previous.yTarget(), (float) previous.zTarget()));

        // Hardcoded catmullrom spline args (for now)
        DoubleArrayList xPoints = new DoubleArrayList(4);

        xPoints.add(previous.xTarget() - (next.xTarget() - previous.xTarget())); // p0 (Get previous-previous point or use previous if not available)
        xPoints.add(previous.xTarget());  // p1
        xPoints.add(next.xTarget());      // p2
        xPoints.add(next.xTarget() + (next.xTarget() - previous.xTarget())); // p3 (Get next-next point or use next if not available)

        // Repeat for y and z axes (duh)
        DoubleArrayList yPoints = new DoubleArrayList(4);

        yPoints.add(previous.yTarget() - (next.yTarget() - previous.yTarget()));
        yPoints.add(previous.yTarget());
        yPoints.add(next.yTarget());
        yPoints.add(next.yTarget() + (next.yTarget() - previous.yTarget()));

        DoubleArrayList zPoints = new DoubleArrayList(4);

        zPoints.add(previous.zTarget() - (next.zTarget() - previous.zTarget()));
        zPoints.add(previous.zTarget());
        zPoints.add(next.zTarget());
        zPoints.add(next.zTarget() + (next.zTarget() - previous.zTarget()));

        // Create transformation context
        AnimationTransformationContext xCtx = new AnimationTransformationContext(
                renderTickTimeStamp,
                timeDiff,
                Either.left(previous.xTarget()),
                next.xTarget(),
                Optional.of(xPoints)
        );
        AnimationTransformationContext yCtx = new AnimationTransformationContext(
                renderTickTimeStamp,
                timeDiff,
                Either.left(previous.yTarget()),
                next.yTarget(),
                Optional.of(yPoints)
        );
        AnimationTransformationContext zCtx = new AnimationTransformationContext(
                renderTickTimeStamp,
                timeDiff,
                Either.left(previous.zTarget()),
                next.zTarget(),
                Optional.of(zPoints)
        );

        // Interpolate between keyframes (lerp)
        float x = (float) BedrockEasing.applyEasingTransformation(next.easing(), xCtx);
        float y = (float) BedrockEasing.applyEasingTransformation(next.easing(), yCtx);
        float z = (float) BedrockEasing.applyEasingTransformation(next.easing(), zCtx);

        return Optional.of(new Vector3f(x, y, z));
    }
}
