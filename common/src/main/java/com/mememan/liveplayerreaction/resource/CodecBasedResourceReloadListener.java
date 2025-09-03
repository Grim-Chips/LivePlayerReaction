package com.mememan.liveplayerreaction.resource;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Map;

public class CodecBasedResourceReloadListener<T> extends SimpleJsonResourceReloadListener { // Only really used on the client (this is client side mod, duh)
    private static final ObjectOpenHashSet<CodecBasedResourceReloadListener<?>> REGISTERED_LISTENERS = new ObjectOpenHashSet<>();
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    @NotNull
    protected final ResourceLocation listenerId;
    @NotNull
    protected final Codec<T> objectCodec;
    @Nullable
    protected Map<ResourceLocation, T> objectMap;

    public CodecBasedResourceReloadListener(ResourceLocation listenerId, @NotNull Codec<T> objectCodec) {
        super(GSON, listenerId.getPath());

        this.listenerId = listenerId;
        this.objectCodec = objectCodec;

        REGISTERED_LISTENERS.add(this);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> serializedObjectMap, ResourceManager resourceManager, ProfilerFiller profileFiller) {
        if (serializedObjectMap.isEmpty()) return;

        Object2ObjectOpenHashMap<ResourceLocation, T> updatedObjectMap = new Object2ObjectOpenHashMap<>(serializedObjectMap.size());

        for (Map.Entry<ResourceLocation, JsonElement> entry : serializedObjectMap.entrySet()) {
            ResourceLocation targetObjId = entry.getKey();

            objectCodec.parse(JsonOps.INSTANCE, entry.getValue())
                    .resultOrPartial(errorMsg -> LOGGER.warn("Failed to parse entry of resource location {} with error: {}", targetObjId, errorMsg))
                    .ifPresent(parsedObj -> updatedObjectMap.put(targetObjId, parsedObj));
        }

        this.objectMap = updatedObjectMap; // We're clearing the whole map + we're using fastutil, not concurrent collections
    }

    @NotNull
    public ResourceLocation getListenerId() {
        return listenerId;
    }

    @NotNull
    public String getDirectory() {
        return getListenerId().getPath();
    }

    @NotNull
    public Codec<T> getObjectCodec() {
        return objectCodec;
    }

    @Nullable
    public Map<ResourceLocation, T> getMappedObjectData() {
        return objectMap;
    }

    public static ImmutableSet<CodecBasedResourceReloadListener<?>> getRegisteredListeners() {
        return ImmutableSet.copyOf(REGISTERED_LISTENERS);
    }
}
