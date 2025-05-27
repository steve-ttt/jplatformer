import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread gameThread;
    private final int FPS = 60;

    private Player player; // Declare Player instance

    // Platform properties
    private int platformX;
    private int platformY;
    private int platformWidth;
    private int platformHeight;

    public GamePanel() {
        setBackground(Color.CYAN); // Placeholder light blue background

        // Initialize player (default values, will be updated later if needed)
        player = new Player(100, 500 - 50, 50, 50); // x, y, width, height

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
        player.update(platformX, platformY, platformWidth, platformHeight, getHeight());
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

        player.draw(g); // Call player's draw method

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
            player.setLeftPressed(true);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player.setRightPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT) {
            player.setLeftPressed(false);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            player.setRightPressed(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used in this game
    }
}
