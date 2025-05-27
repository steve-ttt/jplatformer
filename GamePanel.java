import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread gameThread;
    private final int FPS = 60;

    // Player movement flags
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;
    private int playerSpeed = 5;

    // Player properties
    private int playerX;
    private int playerY;
    private float playerVelocityY = 0; // Vertical velocity
    private float gravity = 0.5f;       // Gravity strength
    private int playerWidth;
    private int playerHeight;

    // Platform properties
    private int platformX;
    private int platformY;
    private int platformWidth;
    private int platformHeight;

    public GamePanel() {
        setBackground(Color.CYAN); // Placeholder light blue background

        // Initialize player properties
        playerWidth = 50;
        playerHeight = 50;
        playerX = 100; // Starting X position
        playerY = 500 - playerHeight; // Starting Y position (assuming 600px height, near bottom)

        // Initialize platform properties
        platformX = 200;
        platformY = 400;
        platformWidth = 150;
        platformHeight = 20;

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
        if (playerX + playerWidth > platformX &&              // Player's right edge is to the right of platform's left edge
            playerX < platformX + platformWidth &&           // Player's left edge is to the left of platform's right edge
            prevPlayerY + playerHeight <= platformY &&       // Player's bottom was at or above platform's top edge PREVIOUSLY
            playerY + playerHeight > platformY &&            // Player's bottom is now below platform's top edge CURRENTLY
            playerVelocityY > 0) {                           // Player is falling

            playerY = platformY - playerHeight; // Place player on top of the platform
            playerVelocityY = 0;                // Stop vertical movement
            onPlatform = true;
        }

        // Apply gravity (if not on a platform that stopped velocity)
        if (!onPlatform) {
            playerVelocityY += gravity;
        }

        // Ground collision (Panel Bottom) - This should remain to catch falls if player misses platform
        int panelHeight = getHeight();
        if (panelHeight > 0 && playerY + playerHeight > panelHeight) { 
            playerY = panelHeight - playerHeight;
            if (playerVelocityY > 0) { // Only stop if falling onto ground
                 playerVelocityY = 0;
            }
        }
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

        // Draw the player
        g.setColor(Color.RED); // Set player color
        g.fillRect(playerX, playerY, playerWidth, playerHeight);

        // Draw the platform
        g.setColor(Color.GREEN); // Set platform color
        g.fillRect(platformX, platformY, platformWidth, platformHeight);

        // The background color is set by setBackground and handled by super.paintComponent(g).
        // If we wanted to draw something else on top of the background, it would go here.
    }

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            isLeftPressed = true;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            isRightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            isLeftPressed = false;
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            isRightPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this game
    }
}
