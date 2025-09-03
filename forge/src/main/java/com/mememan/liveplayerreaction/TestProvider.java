package com.mememan.liveplayerreaction;

import com.mememan.liveplayerreaction.api.keyframe.KeyframeType;
import com.mememan.liveplayerreaction.api.math.easings.BedrockEasing;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationBoneKeyframeData;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationCollection;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationData;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationKeyframeData;
import com.mememan.liveplayerreaction.registry.LPRResourceReloadListeners;
import com.mojang.datafixers.util.Either;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestProvider extends CodecBasedDataProvider<AnimationCollection> {

    public TestProvider(PackOutput targetOutput) {
        super(targetOutput, LPRResourceReloadListeners.ANIMATION_RELOAD_LISTENER);
    }

    @Override
    protected void addDataEntries() {
        putElement(LPRConstants.prefix("fuh"), new AnimationCollection(
                Optional.of(Map.of(
                        "You're NOT him little bro",
                        new AnimationData(
                                Optional.of(5.0D),
                                Optional.of(5.0D),
                                Optional.empty(),
                                Optional.of(false),
                                Optional.empty(),
                                Optional.of(
                                        Map.of(
                                                "fuh",
                                                new AnimationBoneKeyframeData(
                                                        Optional.of(Map.of(
                                                                KeyframeType.POSITION,
                                                                Either.right(
                                                                        new AnimationKeyframeData(Optional.of(
                                                                                Map.of(
                                                                                        0.3172D,
                                                                                        Either.left(
                                                                                                new AnimationKeyframeData.PrimitiveKeyframeValueData(
                                                                                                        Either.left("math.sin(90) * 45"),
                                                                                                        Either.right(20.0D),
                                                                                                        Either.left("math.sin(90) * 45")
                                                                                                )
                                                                                        ),
                                                                                        9.5D,
                                                                                        Either.right(new AnimationKeyframeData.VerboseKeyframeValueData(
                                                                                                Optional.empty(),
                                                                                                Optional.of(
                                                                                                        List.of(
                                                                                                                Either.left("math.sin(90) * 45"),
                                                                                                                Either.right(20.0D),
                                                                                                                Either.left("math.sin(90) * 45")
                                                                                                        )),
                                                                                                Optional.of(
                                                                                                        BedrockEasing.CATMULLROM
                                                                                                ))),
                                                                                        109D,
                                                                                        Either.right(new AnimationKeyframeData.VerboseKeyframeValueData(
                                                                                                Optional.of(List.of(
                                                                                                        Either.left("math.sin(90) * 45"),
                                                                                                        Either.right(20.0D),
                                                                                                        Either.left("math.sin(90) * 45")
                                                                                                )),
                                                                                                Optional.empty(),
                                                                                                Optional.empty()))
                                                                                ))
                                                                        )
                                                                ))
                                                        )
                                                ),
                                                "bih",
                                                new AnimationBoneKeyframeData(
                                                        Optional.of(Map.of(
                                                                KeyframeType.POSITION,
                                                                Either.left(
                                                                        List.of(
                                                                                Either.left(2.0D),
                                                                                Either.right("math.cos(67) * 42"),
                                                                                Either.left(5.0D)
                                                                        )
                                                                ))
                                                        )
                                                )
                                        )
                                )
                        ))
                )));
    }
}
