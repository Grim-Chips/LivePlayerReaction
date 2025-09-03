package com.mememan.liveplayerreaction;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LPRConstants.MOD_ID)
public class LivePlayerReactionForge {
    
    public LivePlayerReactionForge(FMLJavaModLoadingContext ctx) {
        LivePlayerReaction.initialize();

        ctx.getModEventBus().addListener(this::data);
    }

    private void data(GatherDataEvent e) {
        DataGenerator gen = e.getGenerator();
        PackOutput output = gen.getPackOutput();

        gen.addProvider(e.includeClient(), new TestProvider(output));
    }
}