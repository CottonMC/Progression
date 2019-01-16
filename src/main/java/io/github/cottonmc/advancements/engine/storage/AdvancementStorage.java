package io.github.cottonmc.advancements.engine.storage;

import io.github.cottonmc.advancements.engine.data.Identifier;

public interface AdvancementStorage {
    boolean isComplete(Identifier advancement);
    void complete(Identifier advancement);
}
