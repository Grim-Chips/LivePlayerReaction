package com.mememan.liveplayerreaction.mixins.model;

import com.mememan.liveplayerreaction.api.animation.molang.MolangQueryRegistrar;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<LE extends LivingEntity> extends HumanoidModel<LE> {

    private PlayerModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"), cancellable = true)
    private void liveplayerreaction$prepareAnimationState(LE entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player playerOwner) MolangQueryRegistrar.bindQueriesFor(playerOwner);

        liveplayerreaction$setDefaultPivotPoints();
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;copyFrom(Lnet/minecraft/client/model/geom/ModelPart;)V", ordinal = 0), cancellable = true)
    private void liveplayerreaction$animateBones(LE entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {

    }

    @Unique
    private void liveplayerreaction$setDefaultPivotPoints() {

    }

    @Unique
    private void liveplayerreaction$scaleForPart(ModelPart targetPart, float xScale, float yScale, float zScale) {
        targetPart.xScale *= xScale;
        targetPart.yScale *= yScale;
        targetPart.zScale *= zScale;
    }
}
