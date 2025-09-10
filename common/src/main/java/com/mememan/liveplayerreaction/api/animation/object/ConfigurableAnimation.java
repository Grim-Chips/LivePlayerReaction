package com.mememan.liveplayerreaction.api.animation.object;

import com.mememan.liveplayerreaction.api.animation.LPRAnimations;
import com.mememan.liveplayerreaction.api.parsing.object.AnimationData;
import org.jetbrains.annotations.NotNull;

public class ConfigurableAnimation {
    protected final String animationName;
    @NotNull
    protected final ImmutableAnimationData immutableAnimationData;

    private ConfigurableAnimation(Builder configBuilder) {
        this.animationName = configBuilder.animationName;
        this.immutableAnimationData = new ImmutableAnimationData(configBuilder.animLength, configBuilder.blendWeight, configBuilder.animSpeed, configBuilder.overridePreviousAnimation, configBuilder.loop);
    }

    public static Builder builder(String animationName) {
        return new Builder(animationName);
    }

    public static ConfigurableAnimation of(String animationName) {
        return builder(animationName).build();
    }

    public static ConfigurableAnimation copyFrom(ConfigurableAnimation existingAnim) {
        return builder(existingAnim.getAnimationName())
                .animLength(existingAnim.getAnimationData().animLength())
                .blendWeight(existingAnim.getAnimationData().blendWeight())
                .animSpeed(existingAnim.getAnimationData().animSpeed())
                .overridePreviousAnimation(existingAnim.getAnimationData().overridePreviousAnimation())
                .loop(existingAnim.getAnimationData().loop())
                .build();
    }

    public String getAnimationName() {
        return animationName;
    }

    @NotNull
    public ImmutableAnimationData getAnimationData() {
        return immutableAnimationData;
    }

    public static class Builder {
        protected final String animationName;
        @NotNull
        protected final AnimationData animationData;
        protected double animLength;
        protected double blendWeight;
        protected double animSpeed;
        protected boolean overridePreviousAnimation;
        protected boolean loop;

        private Builder(String animationName) {
            this.animationName = animationName;
            this.animationData = LPRAnimations.getAnimationData(animationName).orElseThrow(() -> new IllegalArgumentException(String.format("Animation '%s' does not exist", animationName)));

            this.animLength = this.animationData.animLength().orElse(Double.MAX_VALUE);
            this.blendWeight = this.animationData.blendWeight().orElse(1.0D);
            this.animSpeed = this.animationData.animTimeUpdate().orElse(1.0D);
            this.overridePreviousAnimation = this.animationData.overridePrevAnim().orElse(false);
            this.loop = this.animationData.loop().orElse(false);
        }

        public Builder animLength(double animLength) {
            this.animLength = animLength;
            return this;
        }

        public Builder blendWeight(double blendWeight) {
            this.blendWeight = blendWeight;
            return this;
        }

        public Builder animSpeed(double animSpeed) {
            this.animSpeed = animSpeed;
            return this;
        }

        public Builder overridePreviousAnimation(boolean overridePreviousAnimation) {
            this.overridePreviousAnimation = overridePreviousAnimation;
            return this;
        }

        public Builder loop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public ConfigurableAnimation build() {
            return new ConfigurableAnimation(this);
        }
    }

    public record ImmutableAnimationData(double animLength, double blendWeight, double animSpeed, boolean overridePreviousAnimation, boolean loop) {}
}
