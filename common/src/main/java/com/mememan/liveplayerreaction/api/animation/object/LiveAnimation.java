package com.mememan.liveplayerreaction.api.animation.object;

import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTarget;
import com.mememan.liveplayerreaction.api.animation.keyframe.KeyframeTargetStack;
import com.mememan.liveplayerreaction.api.animation.transform.AnimationTransformationContext;
import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface LiveAnimation {

    @NotNull
    ConfigurableAnimation getAnimationConfig();

    @NotNull
    KeyframeTargetStack getTargetStack();

    default Optional<Vector3f> getTransformationAt(double tickTimeStamp, String boneName, KeyframeType transformationType) {
        KeyframeTargetStack targetStack = getTargetStack();

        if (targetStack == null) return Optional.empty();

        Map<String, KeyframeTarget> targets = targetStack.organizedTargets();

        if (targets.isEmpty()) return Optional.empty();

        // Filter targets for the specific bone and transformation type
        List<KeyframeTarget> relevantTargets = targets.entrySet().stream()
                .filter(curEntry -> curEntry.getKey().equals(boneName) && curEntry.getValue().keyframeType() == transformationType)
                .map(Map.Entry::getValue)
                .sorted(Comparator.comparingDouble(KeyframeTarget::animRenderTick))
                .collect(Collectors.toCollection(ObjectArrayList::new));

        if (relevantTargets.isEmpty()) return Optional.empty();

        KeyframeTarget previous = null;
        KeyframeTarget next = null;

        // Find surrounding keyframes
        for (KeyframeTarget target : relevantTargets) {
            if (target.animRenderTick() == tickTimeStamp) return Optional.of(new Vector3f((float) target.xTarget(), (float) target.yTarget(), (float) target.zTarget())); // Exact match found

            if (target.animRenderTick() < tickTimeStamp) previous = target;
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

        // Calculate progress
        double timeDiff = next.animRenderTick() - previous.animRenderTick();
        double progress = (tickTimeStamp - previous.animRenderTick()) / timeDiff;

        // Create transformation context
        AnimationTransformationContext context = new AnimationTransformationContext(
                tickTimeStamp,
                timeDiff,
                Either.left(0.0),
                progress,
                Optional.empty()
        );

        // Apply easing function from the next keyframe
        double easedProgress = next.easing().ease(context).apply(progress);

        // Interpolate between keyframes (lerp)
        float x = (float) (previous.xTarget() + (next.xTarget() - previous.xTarget()) * easedProgress);
        float y = (float) (previous.yTarget() + (next.yTarget() - previous.yTarget()) * easedProgress);
        float z = (float) (previous.zTarget() + (next.zTarget() - previous.zTarget()) * easedProgress);

        return Optional.of(new Vector3f(x, y, z));
    }
}
