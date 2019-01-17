package io.github.cottonmc.advancements.engine.events;

import io.github.cottonmc.advancements.engine.data.Identifier;


public abstract class EventBase implements Event {
    private Identifier advancement;

    public EventBase(Identifier advancement) {
        this.advancement = advancement;
    }

    @Override
    public Identifier getAdvancement() {
        return advancement;
    }
}
