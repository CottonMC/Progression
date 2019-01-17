package io.github.cottonmc.advancements.engine;

import io.github.cottonmc.advancements.engine.data.Advancement;
import io.github.cottonmc.advancements.engine.data.Identifier;
import io.github.cottonmc.advancements.engine.events.Event;
import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;

import java.util.*;
import java.util.function.Consumer;

/**
 * The manager class that stores and organises the advancements
 */
public class AdvancementEngine<T extends AdvancementStorage> {


    private Map<Identifier, Advancement<T>> storage;

    private List<Consumer<Advancement<T>>> callbacks;

    private Map<Identifier, Event<T>> eventMap;

    public AdvancementEngine() {
        storage = new HashMap<>();
        callbacks = new LinkedList<>();
        eventMap = new HashMap<>();
    }

    public void addAdvancement(Advancement<T> advancement) {
        storage.put(advancement.getID(), advancement);
        for (Consumer<Advancement<T>> addCallback : callbacks) {
            addCallback.accept(advancement);
        }
    }

    public Advancement<T> getAdvancement(Identifier identifier) {
        return storage.get(identifier);
    }

    public void addCallback(Consumer<Advancement<T>> callback) {
        callbacks.add(callback);
    }

    public void addEventhandler(Event<T> event) {
        Identifier advancement = event.getAdvancement();
        if (storage.containsKey(advancement)) {
            eventMap.put(advancement, event);
        }
    }

    private boolean canComplete(Identifier advancement, AdvancementStorage clientStorage, boolean isNested) {
        if (!this.storage.containsKey(advancement))
            return false;

        Advancement<T> advancementInstance = this.storage.get(advancement);

        if (advancementInstance.hasParents()) {
            for (Identifier identifier : advancementInstance.requiredAdvancements()) {
                if (!this.storage.containsKey(identifier))
                    return false;

                if (!canComplete(identifier, clientStorage, true))
                    return false;
            }
        }

        if (isNested)
            return clientStorage.isComplete(advancement);
        return true;
    }

    public boolean canComplete(Identifier advancement, AdvancementStorage clientStorage) {

        boolean canComplete = canComplete(advancement, clientStorage, false);

        if (canComplete) {
            if (eventMap.containsKey(advancement)) {
                eventMap.get(advancement).fire((T) clientStorage);
            }
            return true;
        } else
            return false;
    }

}
