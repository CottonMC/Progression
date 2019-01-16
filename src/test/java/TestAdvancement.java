import io.github.cottonmc.advancements.engine.data.Advancement;
import io.github.cottonmc.advancements.engine.data.Identifier;
import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;

public class TestAdvancement implements Advancement {
    private Identifier identifier;

    TestAdvancement(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public Identifier getID() {
        return identifier;
    }

    @Override
    public boolean isCompleted(AdvancementStorage container) {
        return container.isComplete(identifier);
    }


    @Override
    public Identifier[] requiredAdvancements() {
        return new Identifier[0];
    }
}
