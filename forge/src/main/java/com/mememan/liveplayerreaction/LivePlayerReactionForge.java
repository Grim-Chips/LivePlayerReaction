package com.mememan.liveplayerreaction;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LPRConstants.MOD_ID)
public class LivePlayerReactionForge {
    
    public LivePlayerReactionForge(FMLJavaModLoadingContext ctx) {
        LivePlayerReaction.initialize();
    }
}