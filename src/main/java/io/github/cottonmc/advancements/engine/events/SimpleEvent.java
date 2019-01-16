package io.github.cottonmc.advancements.engine.events;

import io.github.cottonmc.advancements.engine.data.Identifier;

public abstract class SimpleEvent implements Event {
    private Identifier advancement;
    @Override
    public boolean isAdvancement(Identifier advancement) {
        return this.advancement.equals(advancement);
    }

}
