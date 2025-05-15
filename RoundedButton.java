import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private final Color backgroundColor;
    private final int cornerRadius;

    public RoundedButton(String text, Color backgroundColor, int cornerRadius) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.WHITE); // Textfarbe
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // kein sichtbarer Rand
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
