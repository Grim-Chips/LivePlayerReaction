package com.mememan.liveplayerreaction;

import com.mememan.liveplayerreaction.resource.FabricCodecBasedResourceReloadListener;
import net.fabricmc.api.ClientModInitializer;

public class LivePlayerReactionClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LivePlayerReaction.initialize();

        FabricCodecBasedResourceReloadListener.registerResourceReloadListeners();
    }
}
