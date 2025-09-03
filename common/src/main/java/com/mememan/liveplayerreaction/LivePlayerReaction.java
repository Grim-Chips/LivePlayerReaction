package com.mememan.liveplayerreaction;

import com.mememan.liveplayerreaction.registry.LPRResourceReloadListeners;

public class LivePlayerReaction {

    public static void initialize() {
        LPRResourceReloadListeners.bootstrap();
    }
}