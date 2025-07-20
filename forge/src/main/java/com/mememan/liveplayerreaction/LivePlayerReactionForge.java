package com.mememan.liveplayerreaction;

import net.minecraftforge.fml.common.Mod;

@Mod(LPRConstants.MOD_ID)
public class LivePlayerReactionForge {
    
    public LivePlayerReactionForge() {
        LivePlayerReaction.initialize();
    }
}