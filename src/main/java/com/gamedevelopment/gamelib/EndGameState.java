package com.gamedevelopment.gamelib;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Font; // For setting font
import java.awt.Color; // For setting color

// Assuming MenuState is in the same package or imported
// import MenuState;

public class EndGameState implements GameState {
    private GameStateManager gsm;
    public static boolean playerWon = false; // Static field to indicate win/loss

    // Screen dimensions (can be centralized later)
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;

    public EndGameState(GameStateManager gsm) {
        this.gsm = gsm;
        System.out.println("EndGameState initialized. Player won: " + playerWon);
    }

    @Override
    public void update() {
        // System.out.println("EndGameState update()");
        // No updates needed for a static end screen
    }

    @Override
    public void render(Graphics g) {
        // System.out.println("EndGameState render()");
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        // Message
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        String message;
        if (playerWon) {
            message = "You Win!";
        } else {
            message = "Game Over";
        }
        int messageWidth = g.getFontMetrics().stringWidth(message);
        g.drawString(message, (PANEL_WIDTH - messageWidth) / 2, PANEL_HEIGHT / 3);

        // Prompt to return
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        String prompt = "Press Enter to Return to Menu";
        int promptWidth = g.getFontMetrics().stringWidth(prompt);
        g.drawString(prompt, (PANEL_WIDTH - promptWidth) / 2, PANEL_HEIGHT / 2);
    }

    @Override
    public void handleInput(KeyEvent e, boolean isPressed) {
        if (!isPressed) {
            return; // Only process key presses, not releases
        }

        int keyCode = e.getKeyCode();
        // System.out.println("EndGameState handleInput() Key: " + keyCode + " Pressed: " + isPressed);

        if (keyCode == KeyEvent.VK_ENTER) {
            System.out.println("EndGameState: Enter pressed - Returning to MenuState.");
            playerWon = false; // Reset win state for next game
            gsm.setState(new MenuState(gsm));
        }
    }
}
