package com.mememan.liveplayerreaction.api.animation.molang;

import com.mememan.liveplayerreaction.LPRConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.unnamed.mocha.runtime.Scope;
import team.unnamed.mocha.runtime.binding.Binding;
import team.unnamed.mocha.runtime.value.NumberValue;
import team.unnamed.mocha.runtime.value.ObjectProperty;
import team.unnamed.mocha.runtime.value.ObjectValue;

public class MolangQueryRegistrar implements ObjectValue { // Primarily meant to be interpreted, hence the direct object registration rather than using bindInstance/JavaObjectBinding (we don't need compilation support for our use case) (Also no need for set() impls or anything)
    public static final String ANIM_TIME_BINDING = "anim_time";
    public static final String LIFE_TIME_BINDING = "life_time";
    public static final String ACTOR_COUNT_BINDING = "actor_count";
    public static final String HEALTH_BINDING = "health";
    public static final String MAX_HEALTH_BINDING = "max_health";
    public static final String GROUND_SPEED_BINDING = "ground_speed";
    public static final String YAW_SPEED = "yaw_speed";

    public static final String IS_ON_GROUND_BINDING = "is_on_ground";
    public static final String IS_IN_WATER_BINDING = "is_in_water";
    public static final String IS_IN_WATER_OR_RAIN_BINDING = "is_in_water_or_rain";
    public static final String IS_ON_FIRE_BINDING = "is_on_fire";

    protected final Player ownerPlayer;
    protected final ClientLevel curLevel;
    protected final Minecraft mc = Minecraft.getInstance();

    @Binding(ANIM_TIME_BINDING)
    public double animTime;
    @Binding(LIFE_TIME_BINDING)
    public double lifeTime;
    @Binding(ACTOR_COUNT_BINDING)
    public int actorCount;
    @Binding(HEALTH_BINDING)
    public double health;
    @Binding(MAX_HEALTH_BINDING)
    public double maxHealth;
    @Binding(GROUND_SPEED_BINDING)
    public double groundSpeed;
    @Binding(YAW_SPEED)
    public double yawSpeed;

    @Binding(IS_ON_GROUND_BINDING)
    public double isOnGround;
    @Binding(IS_IN_WATER_BINDING)
    public double isInWater;
    @Binding(IS_IN_WATER_OR_RAIN_BINDING)
    public double isInWaterOrRain;
    @Binding(IS_ON_FIRE_BINDING)
    public double isOnFire;

    public MolangQueryRegistrar(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
        this.curLevel = ownerPlayer.level() instanceof ClientLevel clientLevel ? clientLevel : mc.level;
    }

    @Override
    public @Nullable ObjectProperty getProperty(@NotNull String name) {
        return switch (name) {
            case ANIM_TIME_BINDING -> ObjectProperty.property(NumberValue.of(0), false); // TODO
            case LIFE_TIME_BINDING -> ObjectProperty.property(NumberValue.of(1), false); // TODO
            case ACTOR_COUNT_BINDING -> ObjectProperty.property(NumberValue.of(curLevel.getEntityCount()), false);
            case HEALTH_BINDING -> ObjectProperty.property(NumberValue.of(ownerPlayer.getHealth()), false);
            case MAX_HEALTH_BINDING -> ObjectProperty.property(NumberValue.of(ownerPlayer.getMaxHealth()), false);
            case GROUND_SPEED_BINDING -> ObjectProperty.property(NumberValue.of(ownerPlayer.getDeltaMovement().horizontalDistance()), false);
            case YAW_SPEED -> ObjectProperty.property(NumberValue.of(2), false); // TODO
            case IS_ON_GROUND_BINDING -> ObjectProperty.property(NumberValue.of(boolToDouble(ownerPlayer.onGround())), false);
            case IS_IN_WATER_BINDING -> ObjectProperty.property(NumberValue.of(boolToDouble(ownerPlayer.isInWater())), false);
            case IS_IN_WATER_OR_RAIN_BINDING -> ObjectProperty.property(NumberValue.of(boolToDouble(ownerPlayer.isInWaterOrRain())), false);
            case IS_ON_FIRE_BINDING -> ObjectProperty.property(NumberValue.of(boolToDouble(ownerPlayer.isOnFire())), false);
            default -> null;
        };
    }

    public static void bindQueriesFor(Player ownerPlayer) {
        Scope globalScope = LPRConstants.MOLANG_EVALUATOR.scope();

        globalScope.set("query", new MolangQueryRegistrar(ownerPlayer));
    }

    public static double boolToDouble(boolean bool) {
        return bool ? 1.0D : 0.0D;
    }
}
