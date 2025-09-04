package com.mememan.liveplayerreaction.events;

import com.mememan.liveplayerreaction.LPRConstants;
import com.mememan.liveplayerreaction.resource.CodecBasedResourceReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LPRConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LPRClientSetupEvents {

    @SubscribeEvent
    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        CodecBasedResourceReloadListener.getRegisteredListeners().forEach(event::registerReloadListener);
    }
}
