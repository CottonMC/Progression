import io.github.cottonmc.advancements.engine.data.Identifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdentifierTest {
    @Test
    void toStringIsCorrect() {
        assertEquals(new Identifier("domain","path").toString(),"domain:path");
    }
}
