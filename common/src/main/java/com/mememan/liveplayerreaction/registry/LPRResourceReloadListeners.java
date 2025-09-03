package com.mememan.liveplayerreaction.registry;

import com.mememan.liveplayerreaction.LPRConstants;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationCollection;
import com.mememan.liveplayerreaction.resource.CodecBasedResourceReloadListener;

public class LPRResourceReloadListeners {

    public static final CodecBasedResourceReloadListener<AnimationCollection> ANIMATION_RELOAD_LISTENER = new CodecBasedResourceReloadListener<>(LPRConstants.prefix("animations"), AnimationCollection.CODEC);

    public static void bootstrap() {
        // NO-OP
    }
}
