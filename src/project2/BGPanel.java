/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

/**
 *
 * @author emort
 */
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;

public class BGPanel extends JPanel {

    private Image image;

    public BGPanel() {
        this.image = new ImageIcon("./resources/hotel_image.jpg").getImage(); // You can replace with your own background image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, getWidth(), getHeight(), this);
    }
}
