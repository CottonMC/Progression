import io.github.cottonmc.advancements.engine.AdvancementEngine;
import io.github.cottonmc.advancements.engine.data.Advancement;
import io.github.cottonmc.advancements.engine.data.Identifier;
import io.github.cottonmc.advancements.engine.events.Event;
import io.github.cottonmc.advancements.engine.storage.AdvancementStorage;
import io.github.cottonmc.advancements.engine.storage.DefaultAdvancementStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class EngineTest {

    private AdvancementEngine advancementEngine;

    @BeforeEach
    void setUp() {
        advancementEngine = new AdvancementEngine();
    }

    @Test
    void canAdd() {
        TestAdvancement testAdvancement = new TestAdvancement(new Identifier("test", "new_advancement"));
        advancementEngine.addAdvancement(testAdvancement);
        assertEquals(advancementEngine.getAdvancement(new Identifier("test", "new_advancement")), testAdvancement);
    }

    @Test
    void callbackIsCalled() {
        Consumer<Advancement> consumer = mock(Consumer.class);
        TestAdvancement testAdvancement = new TestAdvancement(new Identifier("test", "new_advancement"));

        advancementEngine.addCallback(consumer);
        advancementEngine.addAdvancement(testAdvancement);

        verify(consumer, times(1)).accept(testAdvancement);
    }

    @Nested
    class Events {
        AdvancementStorage advancementStorage;

        @BeforeEach
        void setUp() {
            advancementStorage = new DefaultAdvancementStorage();
        }

        @Test
        void isEventFired() {
            Event event = mock(Event.class);
            when(event.getAdvancement()).thenReturn(new Identifier("test", "new_advancement"));
            TestAdvancement testAdvancement = new TestAdvancement(new Identifier("test", "new_advancement"));
            advancementEngine.addAdvancement(testAdvancement);
            advancementEngine.addEventhandler(event);
            advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage);

            verify(event,times(1)).fire(advancementStorage);
        }

        @Test
        void oneEventFired() {
            TestAdvancement testAdvancement = new TestAdvancement(new Identifier("test", "new_advancement"));
            TestAdvancement testAdvancement2 = new TestAdvancement(new Identifier("test", "advancement"));

            advancementEngine.addAdvancement(testAdvancement);
            advancementEngine.addAdvancement(testAdvancement2);

            Event event = mock(Event.class);
            Event unusedEvent = mock(Event.class);
            when(event.getAdvancement()).thenReturn(new Identifier("test", "new_advancement"));
            when(unusedEvent.getAdvancement()).thenReturn(new Identifier("test", "advancement"));
            advancementEngine.addEventhandler(event);
            advancementEngine.addEventhandler(unusedEvent);


            advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage);

            verify(event, times(1)).fire(advancementStorage);
            verify(unusedEvent,never()).fire(advancementStorage);
        }

        @Test
        void eventCantFireIfAdvancementDoesNotExist(){
            Event event = mock(Event.class);
            when(event.getAdvancement()).thenReturn(new Identifier("test", "new_advancement"));
            advancementEngine.addEventhandler(event);
            advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage);

            verify(event,never()).fire(advancementStorage);
        }

        @Test
        void eventCantFireIfaddedBeforeAdvancement(){
            Event event = mock(Event.class);
            when(event.getAdvancement()).thenReturn(new Identifier("test", "new_advancement"));
            TestAdvancement testAdvancement = new TestAdvancement(new Identifier("test", "new_advancement"));
            advancementEngine.addEventhandler(event);
            advancementEngine.addAdvancement(testAdvancement);

            advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage);

            verify(event,never()).fire(advancementStorage);
        }

    }

    @Nested
    class Completition {
        AdvancementStorage advancementStorage;

        @BeforeEach
        void setUp() {
            advancementStorage = new DefaultAdvancementStorage();
        }

        @Test
        void passForCompletition() {
            TestAdvancement testAdvancement = new TestAdvancement(new Identifier("test", "new_advancement"));
            advancementEngine.addAdvancement(testAdvancement);
            assertTrue(advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage));
        }

        @DisplayName("check fails if advancement does not exists")
        @Test
        void checkFailsIfAdvancementDoesNotExists() {
            assertFalse(advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage));
        }

        @DisplayName("check fails if advancement parent does not exists")
        @Test
        void failsIfParentsNotExist() {
            Identifier[] testAdvancements = {
                    new Identifier("test", "parent")
            };
            TestAdvancementWithParents testAdvancement = new TestAdvancementWithParents(new Identifier("test", "new_advancement"), testAdvancements);
            advancementEngine.addAdvancement(testAdvancement);
            assertFalse(advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage));
        }

        @DisplayName("check fails if advancement parents not completed")
        @Test
        void failsIfParentsNotComplete() {
            Identifier[] testAdvancements = {
                    new Identifier("test", "parent")
            };
            TestAdvancement testAdvancementparent = new TestAdvancement(new Identifier("test", "parent"));
            TestAdvancementWithParents testAdvancement = new TestAdvancementWithParents(new Identifier("test", "new_advancement"), testAdvancements);

            advancementEngine.addAdvancement(testAdvancementparent);
            advancementEngine.addAdvancement(testAdvancement);

            assertFalse(advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage));
        }

        @DisplayName("check passes if advancement parents are completed")
        @Test
        void passIfparentsComplete() {
            Identifier[] testAdvancements = {
                    new Identifier("test", "parent")
            };
            TestAdvancement testAdvancementparent = new TestAdvancement(new Identifier("test", "parent"));
            TestAdvancementWithParents testAdvancement = new TestAdvancementWithParents(new Identifier("test", "new_advancement"), testAdvancements);
            advancementStorage.complete(new Identifier("test", "parent"));

            advancementEngine.addAdvancement(testAdvancementparent);
            advancementEngine.addAdvancement(testAdvancement);

            assertTrue(advancementEngine.canComplete(new Identifier("test", "new_advancement"), advancementStorage));
        }
    }
}