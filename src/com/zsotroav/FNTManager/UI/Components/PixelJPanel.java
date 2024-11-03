package com.zsotroav.FNTManager.UI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

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
            System.out.println("mouseClicked: X=" + e.getX() + ", Y=" + e.getY());
            togglePx(e.getX(), e.getY());
        }
        public void mousePressed(MouseEvent e)  { /* Unused */ }
        public void mouseReleased(MouseEvent e) { /* Unused */ }
        public void mouseEntered(MouseEvent e)  { /* Unused */ }
        public void mouseExited(MouseEvent e)   { /* Unused */ }
    }

    private void togglePx(int x, int y) {
        if (readOnly) return;

        gfx.setColor( (img.getRGB(x, y) == brushColor.getRGB()) ? backgroundColor : brushColor);

        gfx.fillRect((x/ step)* step, (y/ step)* step, step, step);
        icon.setImage(img);
        updateUI();
    }

    ////////////////////////////////////////////////////////////////////////////

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

    private PixelJPanel() {
        setOpaque(true);
        this.setLayout(new GridBagLayout());
    }

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

    public PixelJPanel(int x, int y) {
        this(x, y, 25, Color.white, Color.black);
    }

    public PixelJPanel(int x, int y, int step) {
        this(x, y, step, Color.white, Color.black);
    }

    public PixelJPanel(boolean[][] arr, int step, Color background, Color brush) {
        this();
        this.step = step;
        backgroundColor = background;
        brushColor = brush;

        setImg(arr);
    }

    public PixelJPanel(boolean[][] arr, int step) {
        this(arr, step, Color.WHITE, Color.BLACK);
    }

    ////////////////////////////////////////////////////////////////////////////
    // METHODS
    ////////////////////////////////////////////////////////////////////////////

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
