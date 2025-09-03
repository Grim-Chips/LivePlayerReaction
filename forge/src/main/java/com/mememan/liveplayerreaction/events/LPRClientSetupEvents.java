package com.mememan.liveplayerreaction.events;

import com.mememan.liveplayerreaction.LPRConstants;
import com.mememan.liveplayerreaction.registry.LPRResourceReloadListeners;
import com.mememan.liveplayerreaction.resource.CodecBasedResourceReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LPRConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LPRClientSetupEvents {

    @SubscribeEvent
    public static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        CodecBasedResourceReloadListener.getRegisteredListeners().forEach(event::registerReloadListener);
    }

    @Mod.EventBusSubscriber(modid = LPRConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class Test {

        @SubscribeEvent
        public static void test(PlayerInteractEvent.RightClickEmpty event) {
            LPRConstants.LOGGER.info("Animation listener data fuh 1: {}", LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().get(LPRConstants.prefix("fuh"))
                    .animations()
                    .get()
                    .get("You're NOT him little bro")
            );

            LPRConstants.LOGGER.info("Animation listener data 1: {}", LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().values().stream()
                    .findFirst()
                    .get()
                    .animations()
                    .get()
                    .get("IDLE")
            );

            LPRConstants.LOGGER.info("Animation listener data 2: {}", LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().values().stream()
                    .findFirst()
                    .get()
                    .animations()
                    .get()
                    .get("BASE_WALK")
            );

            LPRConstants.LOGGER.info("Animation listener data 3: {}", LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().values().stream()
                    .findFirst()
                    .get()
                    .animations()
                    .get()
                    .get("BASE_RUN")
            );

            LPRConstants.LOGGER.info("Animation listener data 4: {}", LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().values().stream()
                    .findFirst()
                    .get()
                    .animations()
                    .get()
                    .get("JUMP")
            );

            LPRConstants.LOGGER.info("Animation listener data 5: {}", LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER.getMappedObjectData().values().stream()
                    .findFirst()
                    .get()
                    .animations()
                    .get()
                    .get("FIRE_RUN_HEAD")
            );
        }
    }
}
