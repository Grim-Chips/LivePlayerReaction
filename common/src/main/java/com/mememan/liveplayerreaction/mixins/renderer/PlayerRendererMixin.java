package com.mememan.liveplayerreaction.mixins.renderer;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

    private PlayerRendererMixin() {
        throw new IllegalAccessError("Attempted to construct mixin class (PlayerRendererMixin)");
    }
}
