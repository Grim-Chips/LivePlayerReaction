package com.mememan.liveplayerreaction.api.animation.state;

import com.mememan.liveplayerreaction.api.animation.object.AnimationState;
import com.mememan.liveplayerreaction.api.animation.object.LiveAnimation;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayFIFOQueue;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.UUID;

public final class AnimationStateMachine {
    private static final Object2ObjectOpenHashMap<UUID, AnimationStateMachine> MAPPED_STATE_MACHINES = new Object2ObjectOpenHashMap<>();
    private final Player ownerPlayer;
    private final ObjectArrayFIFOQueue<LiveAnimation> sequentialAnimations = new ObjectArrayFIFOQueue<>();
    private final Object2ObjectOpenHashMap<LiveAnimation, AnimationState> asyncAnimations = new Object2ObjectOpenHashMap<>();

    public AnimationStateMachine(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    public void update() {

    }

    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    public static Optional<AnimationStateMachine> get(Player player) {
        return Optional.ofNullable(MAPPED_STATE_MACHINES.get(player.getUUID()));
    }

    public static AnimationStateMachine getOrCreate(Player player) {
        return MAPPED_STATE_MACHINES.computeIfAbsent(player.getUUID(), uuid -> new AnimationStateMachine(player));
    }
}
