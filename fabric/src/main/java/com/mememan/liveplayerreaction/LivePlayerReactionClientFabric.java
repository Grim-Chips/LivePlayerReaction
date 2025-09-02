package com.mememan.liveplayerreaction;

import net.fabricmc.api.ClientModInitializer;

public class LivePlayerReactionClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LivePlayerReaction.initialize();
    }
}
