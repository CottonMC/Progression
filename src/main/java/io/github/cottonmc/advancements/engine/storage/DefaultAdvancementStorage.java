package io.github.cottonmc.advancements.engine.storage;

import io.github.cottonmc.advancements.engine.data.Identifier;

import java.util.LinkedList;
import java.util.List;

public class DefaultAdvancementStorage implements AdvancementStorage {
    private List<Identifier> advancements;

    public DefaultAdvancementStorage() {
        advancements = new LinkedList<>();
    }

    @Override
    public boolean isComplete(Identifier advancement) {
        return advancements.contains(advancement);
    }

    @Override
    public void complete(Identifier advancement) {
        if(!isComplete(advancement))
            advancements.add(advancement);
    }
}
