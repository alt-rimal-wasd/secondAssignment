package project2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BGPanelTest {

    public BGPanelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // Test of paintComponent method, of class BGPanel.
    //got help from chat gpt
    @Test
    public void testPaintComponent() {
        System.out.println("paintComponent");

        // Create a BufferedImage to get a Graphics object
        BufferedImage bufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();

        BGPanel instance = new BGPanel();
        instance.paintComponent(g);
    }

}
