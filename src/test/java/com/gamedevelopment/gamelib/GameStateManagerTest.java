package com.gamedevelopment.gamelib; // Added package declaration

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Graphics; // Required for GameState interface
import java.awt.event.KeyEvent; // Required for GameState interface

// Note to user:
// To compile and run these JUnit 5 tests, you need to have JUnit Jupiter (JUnit 5)
// integrated into your project.
//
// If you are using Maven, add the following dependency to your pom.xml:
// <dependency>
//     <groupId>org.junit.jupiter</groupId>
//     <artifactId>junit-jupiter-api</artifactId>
//     <version>5.10.0</version> <!-- Or the latest version -->
//     <scope>test</scope>
// </dependency>
// <dependency>
//     <groupId>org.junit.jupiter</groupId>
//     <artifactId>junit-jupiter-engine</artifactId>
//     <version>5.10.0</version> <!-- Or the latest version -->
//     <scope>test</scope>
// </dependency>
//
// If you are using Gradle, add the following to your build.gradle:
// testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.0' // Or the latest version
// testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.0' // Or the latest version
//
// If managing JARs manually, download junit-jupiter-api.jar and junit-jupiter-engine.jar
// and include them in your classpath during compilation and execution.

public class GameStateManagerTest {

    private GameStateManager gsm;

    // Mock/Stub GameState implementation for testing
    static class MockGameState implements GameState {
        boolean updateCalled = false;
        boolean renderCalled = false;
        boolean handleInputCalled = false;
        int updateCount = 0;
        int renderCount = 0;
        int handleInputCount = 0;

        @Override
        public void update() {
            updateCalled = true;
            updateCount++;
        }

        @Override
        public void render(Graphics g) {
            renderCalled = true;
            renderCount++;
        }

        @Override
        public void handleInput(KeyEvent e, boolean isPressed) {
            handleInputCalled = true;
            handleInputCount++;
        }

        public void reset() {
            updateCalled = false;
            renderCalled = false;
            handleInputCalled = false;
            updateCount = 0;
            renderCount = 0;
            handleInputCount = 0;
        }
    }

    @BeforeEach
    void setUp() {
        gsm = new GameStateManager();
    }

    @Test
    void testPushState() {
        MockGameState state1 = new MockGameState();
        MockGameState state2 = new MockGameState();

        gsm.pushState(state1);
        gsm.pushState(state2);

        gsm.update(); // Should call update on state2

        assertTrue(state2.updateCalled, "Top state (state2) update should be called.");
        assertEquals(1, state2.updateCount, "Top state (state2) updateCount should be 1.");
        assertFalse(state1.updateCalled, "Underlying state (state1) update should not be called.");
        assertEquals(0, state1.updateCount, "Underlying state (state1) updateCount should be 0.");
    }

    @Test
    void testPopState() {
        MockGameState state1 = new MockGameState();
        MockGameState state2 = new MockGameState();

        gsm.pushState(state1);
        gsm.pushState(state2);

        gsm.popState(); // state2 is popped, state1 is now active
        gsm.update();

        assertTrue(state1.updateCalled, "State1 update should be called after popping state2.");
        assertEquals(1, state1.updateCount, "State1 updateCount should be 1.");
        assertFalse(state2.updateCalled, "Popped state (state2) update should not be called.");
        assertEquals(0, state2.updateCount, "Popped state (state2) updateCount should be 0.");

        state1.reset(); // Reset state1 for the next check

        gsm.popState(); // state1 is popped, stack is empty
        gsm.update();   // Should not call update on any state

        assertFalse(state1.updateCalled, "State1 update should not be called after being popped.");
        assertEquals(0, state1.updateCount, "State1 updateCount should remain 0 after being popped.");
        assertDoesNotThrow(() -> gsm.update(), "Update on empty stack should not throw.");
    }

    @Test
    void testSetState() {
        MockGameState state1 = new MockGameState();
        MockGameState state2 = new MockGameState();
        // MockGameState state3 = new MockGameState(); // Not strictly needed for this test logic

        gsm.pushState(state1); // Stack: [state1]
        gsm.setState(state2);  // Stack: [state2] (state1 should be cleared)

        gsm.update();

        assertTrue(state2.updateCalled, "Current state (state2) update should be called.");
        assertEquals(1, state2.updateCount, "Current state (state2) updateCount should be 1.");
        assertFalse(state1.updateCalled, "Previous state (state1) update should not be called.");
        assertEquals(0, state1.updateCount, "Previous state (state1) updateCount should be 0.");

        // Assert stack size is 1. This requires inspecting the GSM's internal stack,
        // which is not directly exposed. We can infer this by popping and checking.
        // Alternatively, if GSM had a getStackSize() method, it would be better.
        // For now, we test behavior: if we pop, then update, nothing should happen.
        gsm.popState(); // state2 is popped.
        state2.reset();
        gsm.update();
        assertFalse(state2.updateCalled, "State2 should not be updated after being popped.");
    }
    
    @Test
    void testStackSizeAfterSetState() {
        // This test specifically checks the stack size after setState.
        // It's a more direct way to assert the stack size aspect from testSetState.
        MockGameState state1 = new MockGameState();
        MockGameState state2 = new MockGameState();

        gsm.pushState(state1);
        gsm.setState(state2);

        // To check stack size is 1:
        // 1. Current state is state2.
        // 2. Pop state2.
        // 3. Stack should be empty.
        gsm.popState(); // Pop state2
        
        // Now try to pop again (should do nothing if stack was size 1)
        // And update should not call anything.
        MockGameState stateAfterPop = new MockGameState(); // A fresh mock to check for calls
        gsm.pushState(stateAfterPop); // Push a temporary state to pop it immediately.
                                      // If the stack was empty after popping state2, this new state is the only one.
        stateAfterPop.reset(); // reset before potential update.
        gsm.popState(); // remove stateAfterPop. Stack should be empty.

        gsm.update(); // Should not call update on anything if stack is truly empty.
        assertFalse(stateAfterPop.updateCalled, "No state should be updated if stack was correctly cleared by setState and then emptied.");
        assertFalse(state1.updateCalled, "Original state1 should not have been called.");
        assertFalse(state2.updateCalled, "Set state state2 should not be called as it was popped.");
    }


    @Test
    void testUpdateDelegation() {
        MockGameState mockState = new MockGameState();
        gsm.pushState(mockState);
        gsm.update();

        assertTrue(mockState.updateCalled, "updateCalled should be true on mockState.");
        assertEquals(1, mockState.updateCount, "updateCount should be 1 on mockState.");
    }

    @Test
    void testRenderDelegation() {
        MockGameState mockState = new MockGameState();
        gsm.pushState(mockState);
        gsm.render(null); // Pass null for Graphics as mock doesn't use it

        assertTrue(mockState.renderCalled, "renderCalled should be true on mockState.");
        assertEquals(1, mockState.renderCount, "renderCount should be 1 on mockState.");
    }

    @Test
    void testHandleInputDelegation() {
        MockGameState mockState = new MockGameState();
        gsm.pushState(mockState);
        gsm.handleInput(null, true); // Pass null for KeyEvent as mock doesn't use it

        assertTrue(mockState.handleInputCalled, "handleInputCalled should be true on mockState.");
        assertEquals(1, mockState.handleInputCount, "handleInputCount should be 1 on mockState.");
    }

    @Test
    void testEmptyStackOperations() {
        // Calls on an empty stack
        assertDoesNotThrow(() -> gsm.update(), "update() on empty stack should not throw.");
        assertDoesNotThrow(() -> gsm.render(null), "render() on empty stack should not throw.");
        assertDoesNotThrow(() -> gsm.handleInput(null, true), "handleInput() on empty stack should not throw.");
        
        // Pop on an empty stack
        assertDoesNotThrow(() -> gsm.popState(), "popState() on empty stack should not throw.");
    }
}
