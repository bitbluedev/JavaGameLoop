package foo.bar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.Point;

class GameWindow extends JFrame {
    private JLabel text;
    private long start;
    private Point location = new Point(10, 10);
    private static final double SPEED_PIXELS_PER_MILLISECOND = 0.1;

    GameWindow() {
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        text = new JLabel("A");
        text.setBounds(10, 10, 10, 10);
        add(text);
    }

    void startLoop() {
        start = System.nanoTime();
        new Thread(this::loop).start();
    }

    private void loop() {
        boolean failed = false;
        while (!failed) {
            try {
                SwingUtilities.invokeAndWait(this::updateUi);
            } catch (Exception e) {
                failed = true;
            }
        }
    }

    private void updateUi() {
        long now = System.nanoTime();
        long elapsedMillis = (now - start) / 1_000_000;
        int distance = (int) (elapsedMillis * SPEED_PIXELS_PER_MILLISECOND);
        location.x = 10 + distance % 100;
        text.setLocation(location);
    }
}

public class GameLoop {
    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
        gameWindow.setVisible(true);
        gameWindow.startLoop();
    }
}
