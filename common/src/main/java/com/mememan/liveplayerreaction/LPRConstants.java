package com.mememan.liveplayerreaction;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.unnamed.mocha.MochaEngine;

import java.util.Locale;

public class LPRConstants {
	public static final String MOD_ID = "liveplayerreaction";
	public static final String MOD_NAME = "Live Player Reaction";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
	public static final ResourceLocation PRIMARY_ANIMATION_COLLECTION = prefix("live_player.animation");
	public static final MochaEngine<?> MOLANG_EVALUATOR = MochaEngine.createStandard();

	public static ResourceLocation prefix(String path){
		return new ResourceLocation(MOD_ID, path.toLowerCase(Locale.ROOT));
	}
}