package com.gamedevelopment.gamelib;

import java.awt.Graphics; // Required for the draw method
import java.awt.Color; // Required for the draw method

public class Player {
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private int playerSpeed = 5;
    private int playerX;
    private int playerY;
    private float playerVelocityY = 0;
    private float gravity = 0.5f;
    private int playerWidth;
    private int playerHeight;

    public Player(int x, int y, int width, int height) {
        this.playerX = x;
        this.playerY = y;
        this.playerWidth = width;
        this.playerHeight = height;
    }

    public int getX() {
        return playerX;
    }

    public int getY() {
        return playerY;
    }

    public int getWidth() {
        return playerWidth;
    }

    public int getHeight() {
        return playerHeight;
    }

    public float getVelocityY() {
        return playerVelocityY;
    }

    public float getGravity() {
        return gravity;
    }

    public void setLeftPressed(boolean leftPressed) {
        isLeftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        isRightPressed = rightPressed;
    }

    public void setY(int y) {
        playerY = y;
    }

    public void setVelocityY(float velocityY) {
        playerVelocityY = velocityY;
    }

    public void update(int platformX, int platformY, int platformWidth, int platformHeight, int panelHeight) {
        // Horizontal movement
        if (isLeftPressed) {
            playerX -= playerSpeed;
        }
        if (isRightPressed) {
            playerX += playerSpeed;
        }

        // Store player's Y position before applying gravity/velocity for this frame
        float prevPlayerY = playerY;

        // Apply vertical velocity
        playerY += playerVelocityY;

        // Platform Collision Detection
        boolean onPlatform = false;
        if (playerX + playerWidth > platformX &&
            playerX < platformX + platformWidth &&
            prevPlayerY + playerHeight <= platformY &&
            playerY + playerHeight > platformY &&
            playerVelocityY > 0) {

            playerY = platformY - playerHeight;
            playerVelocityY = 0;
            onPlatform = true;
        }

        // Apply gravity (if not on a platform that stopped velocity)
        if (!onPlatform) {
            playerVelocityY += getGravity(); // Use getter for gravity
        }

        // Ground collision (Panel Bottom)
        if (panelHeight > 0 && playerY + playerHeight > panelHeight) {
            playerY = panelHeight - playerHeight;
            if (playerVelocityY > 0) {
                playerVelocityY = 0;
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED); // Set player color
        g.fillRect(playerX, playerY, playerWidth, playerHeight);
    }
}
