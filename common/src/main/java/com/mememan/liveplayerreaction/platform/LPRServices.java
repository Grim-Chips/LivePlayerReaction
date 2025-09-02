package com.mememan.liveplayerreaction.platform;

import com.mememan.liveplayerreaction.LPRConstants;
import com.mememan.liveplayerreaction.platform.services.PlatformHooks;

import java.util.ServiceLoader;

public class LPRServices {
    public static final PlatformHooks PLATFORM_HOOKS = loadService(PlatformHooks.class);

    public static <T> T loadService(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LPRConstants.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);

        return loadedService;
    }
}