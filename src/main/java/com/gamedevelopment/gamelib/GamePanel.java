package com.gamedevelopment.gamelib;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Imports for Game State Management
import java.awt.event.KeyEvent; // Already present, but good to note for GSM
// import GameStateManager; // Will be implicitly handled by package or assume same directory
// import MenuState; // Will be implicitly handled by package or assume same directory


public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread gameThread;
    private final int FPS = 60;

    private GameStateManager gsm; // Game State Manager

    // Player and platform properties are now managed by individual states

    public GamePanel() {
        setBackground(Color.CYAN); // Placeholder light blue background

        // Initialize GameStateManager and set initial state
        gsm = new GameStateManager();
        gsm.setState(new MenuState(gsm)); // Assuming MenuState is in the same package or imported

        // Setup for keyboard input
        setFocusable(true);
        addKeyListener(this);

        startGameLoop();
    }

    public void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Game logic update method
    public void updateGame() {
        gsm.update();
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        long lastFrame = System.nanoTime();
        long now;

        while (true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                updateGame();
                repaint();
                lastFrame = now;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Draws the background

        gsm.render(g); // Delegate rendering to the current game state

        // The background color is set by setBackground and handled by super.paintComponent(g).
    }

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        gsm.handleInput(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsm.handleInput(e, false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this game
    }
}
