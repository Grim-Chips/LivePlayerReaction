package com.mememan.liveplayerreaction.platform;

import com.mememan.liveplayerreaction.LPRConstants;

import java.util.ServiceLoader;

public class LPRServices {

    public static <T> T loadService(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        LPRConstants.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);

        return loadedService;
    }
}