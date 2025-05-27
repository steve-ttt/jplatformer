import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.Font; // For setting font
import java.awt.Color; // For setting color

// Assuming Level1State is in the same package or imported
// import Level1State;

public class MenuState implements GameState {
    private GameStateManager gsm;

    // Screen dimensions (can be centralized later)
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 600;

    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;
        System.out.println("MenuState initialized");
    }

    @Override
    public void update() {
        // System.out.println("MenuState update()");
        // No updates needed for a static menu
    }

    @Override
    public void render(Graphics g) {
        // System.out.println("MenuState render()");
        // Background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        // Title
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        String title = "Main Menu";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (PANEL_WIDTH - titleWidth) / 2, PANEL_HEIGHT / 4);

        // Menu Options
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        String option1 = "Press 1 to Start";
        String option2 = "Press 2 to Exit";

        int option1Width = g.getFontMetrics().stringWidth(option1);
        int option2Width = g.getFontMetrics().stringWidth(option2);

        g.drawString(option1, (PANEL_WIDTH - option1Width) / 2, PANEL_HEIGHT / 2);
        g.drawString(option2, (PANEL_WIDTH - option2Width) / 2, PANEL_HEIGHT / 2 + 40);
    }

    @Override
    public void handleInput(KeyEvent e, boolean isPressed) {
        if (!isPressed) {
            return; // Only process key presses, not releases
        }

        int keyCode = e.getKeyCode();
        // System.out.println("MenuState handleInput() Key: " + keyCode + " Pressed: " + isPressed);

        if (keyCode == KeyEvent.VK_1) {
            System.out.println("MenuState: Key 1 pressed - Starting Level1State.");
            gsm.setState(new Level1State(gsm));
        } else if (keyCode == KeyEvent.VK_2) {
            System.out.println("MenuState: Key 2 pressed - Exiting application.");
            System.exit(0);
        }
    }
}
