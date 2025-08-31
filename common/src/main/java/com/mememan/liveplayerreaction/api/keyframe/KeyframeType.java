package com.mememan.liveplayerreaction.api.keyframe;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum KeyframeType implements StringRepresentable {
    ROTATION,
    POSITION,
    SCALE;

    public static final Codec<KeyframeType> CODEC = StringRepresentable.fromEnum(KeyframeType::values);

    KeyframeType() {

    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase();
    }
}
