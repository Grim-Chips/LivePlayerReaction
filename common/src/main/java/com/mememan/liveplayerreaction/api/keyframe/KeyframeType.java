package com.mememan.liveplayerreaction.api.keyframe;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum KeyframeType implements StringRepresentable {
    ROTATION,
    POSITION,
    SCALE;

    public static final Codec<KeyframeType> CODEC = StringRepresentable.fromEnum(KeyframeType::values);

    KeyframeType() {

    }

    @Override
    public String getSerializedName() {
        return name().toLowerCase();
    }
}
