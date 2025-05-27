package com.gamedevelopment.gamelib;

import java.util.Stack;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class GameStateManager {
    private Stack<GameState> states;

    public GameStateManager() {
        states = new Stack<GameState>();
    }

    public void pushState(GameState state) {
        states.push(state);
    }

    public void popState() {
        if (!states.isEmpty()) {
            states.pop();
        }
    }

    public void setState(GameState state) {
        states.clear();
        states.push(state);
    }

    public void update() {
        if (!states.isEmpty()) {
            states.peek().update();
        }
    }

    public void render(Graphics g) {
        if (!states.isEmpty()) {
            states.peek().render(g);
        }
    }

    public void handleInput(KeyEvent e, boolean isPressed) {
        if (!states.isEmpty()) {
            states.peek().handleInput(e, isPressed);
        }
    }
}
