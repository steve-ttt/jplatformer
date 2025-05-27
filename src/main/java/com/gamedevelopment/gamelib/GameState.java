import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface GameState {
    void update();
    void render(Graphics g);
    void handleInput(KeyEvent e, boolean isPressed);
}
