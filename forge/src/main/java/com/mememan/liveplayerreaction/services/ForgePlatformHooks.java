package com.mememan.liveplayerreaction.services;

import com.mememan.liveplayerreaction.platform.services.PlatformHooks;
import net.minecraftforge.fml.ModList;

public class ForgePlatformHooks implements PlatformHooks {

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }
}
