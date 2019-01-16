import io.github.cottonmc.advancements.engine.data.Advancement;
import io.github.cottonmc.advancements.engine.data.Identifier;
import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;

public class TestAdvancementWithParents implements Advancement {
    private Identifier identifier;
    private Identifier[] parents;

    public TestAdvancementWithParents(Identifier identifier, Identifier[] parents) {
        this.identifier = identifier;
        this.parents = parents;
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
        return parents;
    }
}
