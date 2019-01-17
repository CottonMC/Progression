package io.github.cottonmc.advancements.engine.events;

import io.github.cottonmc.advancements.engine.data.Identifier;
import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;

public interface Event<T extends AdvancementStorage> {
    Identifier getAdvancement();
    void fire(T storage);
}
