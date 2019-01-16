package io.github.cottonmc.advancements.engine.data;

import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;

public interface Advancement<T extends AdvancementStorage> {

    Identifier getID();

    boolean isCompleted(T container);

    Identifier[] requiredAdvancements();

    default boolean hasRequired(){
        return requiredAdvancements().length >0;
    }
}
