package com.zsotroav.FNTManager.UI.Components;

import javax.swing.*;
import java.awt.*;

public class ImageJPanel extends JPanel {
    public ImageJPanel(String image)
    {
        setOpaque(true);
        setBackground(Color.BLACK);

        ImageIcon icon = new ImageIcon(image);
        this.add(new JLabel(icon));
    }
}
