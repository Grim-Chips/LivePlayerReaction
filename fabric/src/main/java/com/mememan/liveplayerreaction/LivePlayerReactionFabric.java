package com.mememan.liveplayerreaction;

import net.fabricmc.api.ModInitializer;

public class LivePlayerReactionFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        LivePlayerReaction.initialize();
    }
}
