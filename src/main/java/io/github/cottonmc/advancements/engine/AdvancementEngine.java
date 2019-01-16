package io.github.cottonmc.advancements.engine;

import io.github.cottonmc.advancements.engine.data.Advancement;
import io.github.cottonmc.advancements.engine.data.Identifier;
import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;

import java.util.*;
import java.util.function.Consumer;

/**
 * The manager class that stores and organises the advancements
 * */
public class AdvancementEngine<T extends AdvancementStorage> {

    //storing them in a map for quick access.
    private Map<Identifier, Advancement<T>> storage;
    //a list that stores functions that will run when adding a new advancement
    private List<Consumer<Advancement<T>>> addCallbacks;
    public AdvancementEngine(){
        storage = new HashMap<>();
        addCallbacks = new LinkedList<>();
    }

    public void addAdvancement(Advancement<T> advancement){
        storage.put(advancement.getID(),advancement);
        for (Consumer<Advancement<T>> addCallback : addCallbacks) {
            addCallback.accept(advancement);
        }
    }

    public Advancement<T> getAdvancement(Identifier identifier){
        return storage.get(identifier);
    }

    public void addAddCallback(Consumer<Advancement<T>> callback){
        addCallbacks.add(callback);
    }

    public boolean canComplete(Identifier advancement, AdvancementStorage clientStorage){
        if(!this.storage.containsKey(advancement))
            return false;

        Advancement<T> advancementInstance = this.storage.get(advancement);

        if(advancementInstance.hasRequired()){
            for (Identifier identifier : advancementInstance.requiredAdvancements()) {
                if(!this.storage.containsKey(identifier))
                    return false;

                if(!canComplete(identifier,clientStorage))
                    return false;
            }
        }

        return true;//advancementInstance.isCompleted((T)storage);
    }
}
