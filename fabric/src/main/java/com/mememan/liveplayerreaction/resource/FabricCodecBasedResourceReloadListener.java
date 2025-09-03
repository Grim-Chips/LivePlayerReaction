package com.mememan.liveplayerreaction.resource;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.jetbrains.annotations.NotNull;

public final class FabricCodecBasedResourceReloadListener<T> extends CodecBasedResourceReloadListener<T> implements IdentifiableResourceReloadListener {

    public FabricCodecBasedResourceReloadListener(ResourceLocation listenerId, @NotNull Codec<T> objectCodec) {
        super(listenerId, objectCodec);
    }

    @Override
    public ResourceLocation getFabricId() {
        return getListenerId();
    }

    public static void registerResourceReloadListeners() {
        CodecBasedResourceReloadListener.getRegisteredListeners().stream()
                .map(curListener -> new FabricCodecBasedResourceReloadListener<>(curListener.getListenerId(), curListener.getObjectCodec()))
                .forEach(curListener -> ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(curListener));
    }
}
