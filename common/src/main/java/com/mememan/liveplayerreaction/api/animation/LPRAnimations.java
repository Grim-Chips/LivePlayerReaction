package com.mememan.liveplayerreaction.api.animation;

import com.google.common.collect.ImmutableSet;
import com.mememan.liveplayerreaction.LPRConstants;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationCollection;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationData;
import com.mememan.liveplayerreaction.registry.LPRResourceReloadListeners;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.Optional;

public final class LPRAnimations {
    private static final ObjectOpenHashSet<String> ANIMATIONS = new ObjectOpenHashSet<>();
    public static final String IDLE = registerAnimation("idle");
    public static final String WATER_IDLE = registerAnimation("water_idle");

    public static final String BACKWARDS_WALK = registerAnimation("backwards_walk");

    public static final String WALK = registerAnimation("walk");
    public static final String WALK_L = registerAnimation("walk_l");
    public static final String WALK_R = registerAnimation("walk_r");

    public static final String WATER_WALK = registerAnimation("water_walk");
    public static final String WATER_WALK_L = registerAnimation("water_walk_l");
    public static final String WATER_WALK_R = registerAnimation("water_walk_r");

    public static final String UNDERWATER_MOVE = registerAnimation("underwater_move");
    public static final String DIVE = registerAnimation("dive");

    public static final String RUN = registerAnimation("run");
    public static final String RUN_L = registerAnimation("run_l");
    public static final String RUN_R = registerAnimation("run_r");

    public static final String PANIC_RUN = registerAnimation("panic_run");

    public static final String FIRE_RUN_L = registerAnimation("fire_run_l");
    public static final String FIRE_RUN_R = registerAnimation("fire_run_r");

    public static final String JUMP = registerAnimation("jump");
    public static final String FALL = registerAnimation("fall");
    public static final String FREEFALL = registerAnimation("freefall");
    public static final String LAND = registerAnimation("land");

    public static final String SNEAK = registerAnimation("sneak");

    public static final String SWING_L = registerAnimation("swing_l");
    public static final String SWING_R = registerAnimation("swing_r");

    public static final String INTERACT_L = registerAnimation("interact_l");
    public static final String INTERACT_R = registerAnimation("interact_r");

    public static final String DEATH = registerAnimation("death");

    private LPRAnimations() {

    }

    public static String registerAnimation(String animName) {
        ANIMATIONS.add(animName);
        return animName;
    }

    public static Optional<AnimationData> getAnimationData(String animName) {
        Optional<AnimationCollection> primaryPlayerAnimCollection = LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData() == null || LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().get(LPRConstants.PRIMARY_ANIMATION_COLLECTION));

        return primaryPlayerAnimCollection.flatMap(curCollection -> curCollection.animations().map(curAnimMap -> curAnimMap.get(animName)));
    }

    public static ImmutableSet<String> getAnimations() {
        return ImmutableSet.copyOf(ANIMATIONS);
    }
}
