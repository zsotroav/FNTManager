package com.zsotroav.FNTManager.UI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Click-to edit pixel graphical display panel
 */
public class PixelJPanel extends JPanel {
    private int step;

    private BufferedImage img;
    private Graphics gfx;

    private ImageIcon icon;
    private boolean readOnly = true;

    private Color brushColor;
    private Color backgroundColor;

    public void setReadOnly(boolean b) { readOnly = b; }
    public boolean isReadOnly() { return readOnly; }

    ////////////////////////////////////////////////////////////////////////////
    // GETTERS
    ////////////////////////////////////////////////////////////////////////////

    public Color getBrushColor() { return brushColor; }
    public Color getBackgroundColor() { return backgroundColor; }

    /**
     * Get the binary representation of the data
     * @return HEIGHT*WIDTH sized boolean array
     */
    public boolean[][] getData() {
        boolean[][] data = new boolean[img.getHeight()/step][img.getWidth()/step];
        for (int i = 0; i < img.getHeight()/step; i++) {
            for (int j = 0; j < img.getWidth()/step; j++) {
                data[i][j] = (img.getRGB(j*step, i*step) == brushColor.getRGB());
            }
        }
        return data;
    }

    ////////////////////////////////////////////////////////////////////////////
    // CLICK HANDLERS
    ////////////////////////////////////////////////////////////////////////////

    class CustomMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            togglePx(e.getX(), e.getY());
        }
        public void mousePressed(MouseEvent e)  { /* Unused */ }
        public void mouseReleased(MouseEvent e) { /* Unused */ }
        public void mouseEntered(MouseEvent e)  { /* Unused */ }
        public void mouseExited(MouseEvent e)   { /* Unused */ }
    }

    /**
     * Toggle a specififc pixel of the image
     * @param x X (horizontal) coordinate
     * @param y Y (vertical) coordinate
     */
    public void togglePx(int x, int y) {
        if (readOnly) return;

        gfx.setColor( (img.getRGB(x, y) == brushColor.getRGB()) ? backgroundColor : brushColor);

        gfx.fillRect((x/ step)* step, (y/ step)* step, step, step);
        icon.setImage(img);
        updateUI();
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * Generate the displayed image icon
     */
    private void createIcon() {
        this.removeAll();
        icon = new ImageIcon(img);
        var l = new JLabel(icon);
        l.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        l.addMouseListener(new CustomMouseListener());

        this.add(l);
        this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));

        this.updateUI();
    }

    ////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTORS
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Basic internal setup
     */
    private PixelJPanel() {
        setOpaque(true);
        this.setLayout(new GridBagLayout());
    }

    /**
     * Generate a PixelJPanel
     * @param x width
     * @param y height
     * @param step scale step (actual pixel is step*step)
     * @param background background color
     * @param brush foreground/brush color
     */
    public PixelJPanel(int x, int y, int step, Color background, Color brush) {
        this();
        backgroundColor = background;
        brushColor = brush;
        this.step = step;

        img = new BufferedImage(x*step, y*step, BufferedImage.TYPE_INT_ARGB);
        gfx = img.getGraphics();
        gfx.setColor(backgroundColor);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());
        createIcon();
    }

    /**
     * Generate a PixelJPanel with default values (scale=25, color=BW)
     * @param x width
     * @param y height
     */
    public PixelJPanel(int x, int y) {
        this(x, y, 25, Color.white, Color.black);
    }

    /**
     * Generate a PixelJPanel with default values (color=BW)
     * @param x width
     * @param y height
     * @param step scale step (actual pixel is step*step)
     */
    public PixelJPanel(int x, int y, int step) {
        this(x, y, step, Color.white, Color.black);
    }

    /**
     * Generate a PixelJPanel with the provided data
     * @param arr boolean array of initial values
     * @param step scale step (actual pixel is step*step)
     * @param background background color
     * @param brush foreground/brush color
     */
    public PixelJPanel(boolean[][] arr, int step, Color background, Color brush) {
        this();
        this.step = step;
        backgroundColor = background;
        brushColor = brush;

        setImg(arr);
    }
/**
     * Generate a PixelJPanel with the provided data and default values (color=BW)
     * @param arr boolean array of initial values
     * @param step scale step (actual pixel is step*step)
     */
    public PixelJPanel(boolean[][] arr, int step) {
        this(arr, step, Color.WHITE, Color.BLACK);
    }

    ////////////////////////////////////////////////////////////////////////////
    // METHODS
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Set the image data for the panel
     * @param arr boolean array of the values
     */
    public void setImg(boolean[][] arr) {
        img = new BufferedImage(arr[0].length*step, arr.length*step, BufferedImage.TYPE_INT_ARGB);
        gfx = img.getGraphics();
        gfx.setColor(backgroundColor);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());
        gfx.setColor(brushColor);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j]) gfx.fillRect(j*step, i*step, step, step);
            }
        }

        createIcon();
    }

    public void setBrushColor(Color c) {
        var data = getData();
        this.brushColor = c;
        setImg(data);
    }

    public void setBackgroundColor(Color c) {
        var data = getData();
        this.backgroundColor = c;
        setImg(data);
    }

    public void setScale(int scale) {
        var data = getData();
        this.step = scale;
        setImg(data);
    }
}
