package com.mememan.liveplayerreaction.api.animation.state;

public final class AnimationStateMachine {
    public static final AnimationStateMachine INSTANCE = new AnimationStateMachine();

    private AnimationStateMachine() {

    }

    public void enqueueAnimation(String animName) {

    }

    public void enqueueAnimations(String... animNames) {
        for (String animName : animNames) {
            enqueueAnimation(animName);
        }
    }
}
