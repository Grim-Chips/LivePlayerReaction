package com.mememan.liveplayerreaction.services;

import com.mememan.liveplayerreaction.platform.services.PlatformHooks;
import net.fabricmc.loader.api.FabricLoader;

public class FabricPlatformHooks implements PlatformHooks {

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }
}
