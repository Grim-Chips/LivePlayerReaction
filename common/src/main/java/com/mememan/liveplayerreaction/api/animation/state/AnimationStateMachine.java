package com.mememan.liveplayerreaction.api.animation.state;

import java.util.UUID;

public final class AnimationStateMachine {
    public static final AnimationStateMachine INSTANCE = new AnimationStateMachine();

    private AnimationStateMachine() {

    }

    public void enqueueAnimation(UUID targetPlayerUUID, String animName) {

    }

    public void enqueueAnimations(UUID targetPlayerUUID, String... animNames) {
        for (String animName : animNames) {
            enqueueAnimation(targetPlayerUUID, animName);
        }
    }
}
