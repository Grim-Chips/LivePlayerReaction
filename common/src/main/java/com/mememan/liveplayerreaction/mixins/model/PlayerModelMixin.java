package com.mememan.liveplayerreaction.mixins.model;

import net.minecraft.client.model.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin {

    private PlayerModelMixin() {
        throw new IllegalAccessError("Attempted to construct mixin class (PlayerModelMixin)");
    }
}
