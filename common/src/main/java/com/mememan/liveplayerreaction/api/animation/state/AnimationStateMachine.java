package com.mememan.liveplayerreaction.api.animation.state;

import com.mememan.liveplayerreaction.api.animation.object.AnimationState;
import com.mememan.liveplayerreaction.api.animation.object.LiveAnimation;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import net.minecraft.world.entity.player.Player;

public final class AnimationStateMachine {
    public static final AnimationStateMachine INSTANCE = new AnimationStateMachine();
    protected final ObjectArrayFIFOQueue<LiveAnimation> sequentialAnimations = new ObjectArrayFIFOQueue<>();
    protected final Object2ObjectOpenHashMap<LiveAnimation, AnimationState> asyncAnimations = new Object2ObjectOpenHashMap<>();

    private AnimationStateMachine() {

    }

    public void updateFor(Player targetPlayer) {

    }
}
