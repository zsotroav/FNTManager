package com.zsotroav.FNTManager.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class PixelJPanel extends JPanel {

    private int Step;

    private BufferedImage img;
    private Graphics gfx;

    private ImageIcon icon;
    private boolean readOnly = false;

    private Color Brush;
    private Color Background;

    public void Lock() { readOnly = true; }
    public void UnLock() { readOnly = false; }


    public boolean[][] getData() {
        boolean[][] data = new boolean[img.getHeight()][img.getWidth()];
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                data[i][j] = (img.getRGB(j*Step, i*Step) == Brush.getRGB());
            }
        }
        return data;
    }


    ////////////////////////////////////////////////////////////////////////////
    /// CLICK HANDLERS
    ////////////////////////////////////////////////////////////////////////////

    class CustomMouseListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            System.out.println("mouseClicked: X=" + e.getX() + ", Y=" + e.getY());
            TogglePx(e.getX(), e.getY());
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }

    private void TogglePx(int x, int y) {
        if (readOnly) return;

        gfx.setColor( (img.getRGB(x, y) == Brush.getRGB()) ? Background : Brush);

        gfx.fillRect((x/ Step)* Step, (y/ Step)* Step, Step, Step);
        icon.setImage(img);
        updateUI();
    }

    ////////////////////////////////////////////////////////////////////////////

    private void CreateIcon() {
        icon = new ImageIcon(img);
        var l = new JLabel(icon);
        l.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        this.add(l);
        this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
    }

    ////////////////////////////////////////////////////////////////////////////
    /// CONSTRUCTORS
    ////////////////////////////////////////////////////////////////////////////

    private PixelJPanel() {
        setOpaque(true);
        this.setBackground(Color.MAGENTA); // Debug color
        this.setLayout(null);

        this.addMouseListener(new CustomMouseListener());
    }

    public PixelJPanel(int x, int y, int step, Color background, Color brush) {
        this();
        Background = background;
        Brush = brush;
        Step = step;

        img = new BufferedImage(x*step, y*step, BufferedImage.TYPE_INT_ARGB);
        gfx = img.getGraphics();
        gfx.setColor(Background);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());
        CreateIcon();
    }

    public PixelJPanel(int x, int y) {
        this(x, y, 100, Color.white, Color.black);
    }

    public PixelJPanel(int x, int y, int step) {
        this(x, y, step, Color.white, Color.black);
    }

    public PixelJPanel(boolean[][] arr, int step, Color background, Color brush) {
        this();
        Step = step;
        Background = background;
        Brush = brush;

        img = new BufferedImage(arr[0].length*step, arr.length*step, BufferedImage.TYPE_INT_ARGB);
        gfx = img.getGraphics();
        gfx.setColor(Background);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());
        gfx.setColor(Brush);
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j]) gfx.fillRect(j*step, i*step, step, step);
            }
        }

        CreateIcon();
    }

    public PixelJPanel(boolean[][] arr, int step) {
        this(arr, step, Color.WHITE, Color.BLACK);
    }

}
