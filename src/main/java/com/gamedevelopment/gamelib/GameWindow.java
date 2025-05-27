import javax.swing.JFrame;

public class GameWindow {

    private JFrame frame;
    private GamePanel gamePanel;

    public GameWindow() {
        frame = new JFrame("Platformer Game");
        gamePanel = new GamePanel(); // Create an instance of GamePanel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(gamePanel); // Add GamePanel to the JFrame
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new GameWindow();
    }
}
