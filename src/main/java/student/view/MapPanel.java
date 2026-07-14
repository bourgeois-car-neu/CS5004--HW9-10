package student.view;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;



/**
 * map panel that plots a latitude/longitude point as a dot.
 */
public class MapPanel extends JPanel {
    /** the latitude to plot. */
    private double latitude;

    /** the longitude to plot. */
    private double longitude;

    /** valid point to draw set to false. */
    private boolean hasPoint = false;
    /** image of map. */
    private Image mapImage;

    /**
     * constructor to add image of map.
     */
    public MapPanel() {
        mapImage = new ImageIcon(getClass().getResource("/BlankMap-World-Equirectangular.png")).getImage();
        System.out.println("Map image loaded: " + (mapImage != null));
    }

    /**
     * updates the point to plot and triggers a redraw.
     * @param latitude latitude to plot
     * @param longitude longitude to plot
     */
    public void setPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.hasPoint = true;
        repaint(); // draws new component
    }

    /**
     * map setup for MapPanel and drawing points.
     * @param graphics the <code>Graphics</code> object to protect
     */
    @Override
    // predefined method signature
    protected void paintComponent(Graphics graphics) {
        // do background painting before custom drawing
        super.paintComponent(graphics);
        Graphics2D graphics2 = (Graphics2D) graphics; // make Graphics into Graphics2D
        int widthPanel = getWidth(); // returns width (px) of panel
        int heightPanel = getHeight(); // // returns height (px) of panel
        if (mapImage != null) {
            graphics2.drawImage(mapImage, 0, 0, widthPanel, heightPanel, this);
        }
        // if no valid point, do nothing
        if (!hasPoint) {
            return;
        }

        // convert longitude into x (px)
        int x = (int) ((longitude + 180) / 360 * widthPanel);
        // convert latitude into y (px)
        int y = (int) ((90 - latitude) / 180 * heightPanel);
        // set color
        graphics2.setColor(Color.PINK);
        // draws a filled circle (10x10) centered around x, y
        // subtract 5 centers the dot on the point
        graphics2.fillOval(x - 5, y - 5, 10, 10);
    }
}
