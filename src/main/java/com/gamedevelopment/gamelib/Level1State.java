package com.gamedevelopment.gamelib;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Color; // Added for background and platform color

// Assuming Player class is in the same package or imported appropriately
// import Player; 

public class Level1State implements GameState {
    private GameStateManager gsm;
    private Player player;

    // Platform properties
    private int platformX;
    private int platformY;
    private int platformWidth;
    private int platformHeight;

    // Game dimensions (hardcoded for now)
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;


    public Level1State(GameStateManager gsm) {
        this.gsm = gsm;
        // Initialize player
        // Adjusted Y position slightly higher than original 500-50, as panel height is 600
        player = new Player(100, PANEL_HEIGHT - 100, 50, 50); // x, y, width, height 

        // Initialize platform properties
        platformX = 200;
        platformY = PANEL_HEIGHT - 200; // Position platform relative to bottom
        platformWidth = 150;
        platformHeight = 20;
        System.out.println("Level1State initialized with player and platform.");
    }

    @Override
    public void update() {
        // System.out.println("Level1State update()");
        player.update(platformX, platformY, platformWidth, platformHeight, PANEL_HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        // System.out.println("Level1State render()");
        // Draw background (optional, GamePanel might also draw a default one)
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        // Draw the player
        player.draw(g);

        // Draw the platform
        g.setColor(Color.GREEN); // Set platform color
        g.fillRect(platformX, platformY, platformWidth, platformHeight);
    }

    @Override
    public void handleInput(KeyEvent e, boolean isPressed) {
        // System.out.println("Level1State handleInput() Key: " + e.getKeyCode() + " Pressed: " + isPressed);
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT) {
            player.setLeftPressed(isPressed);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player.setRightPressed(isPressed);
        }
        // Example: Jump (ensure Player class has a jump method)
        // if (keyCode == KeyEvent.VK_SPACE && isPressed) {
        //     player.jump(); 
        // }

        if (isPressed && keyCode == KeyEvent.VK_ESCAPE) {
            System.out.println("Escape pressed in Level1State - returning to MenuState.");
            gsm.setState(new MenuState(gsm)); // Go back to menu
        }
    }
}
