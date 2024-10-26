package project2;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BGPanel extends JPanel {
    private Image image;

    // Constructor to load the background image
    public BGPanel() {
        this.image = new ImageIcon("./resources/hotel_image.jpg").getImage(); // Replace with your background image
    }

    // Draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
}
