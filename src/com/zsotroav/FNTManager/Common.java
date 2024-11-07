package com.zsotroav.FNTManager;

import javax.swing.*;
import java.awt.*;

public class Common {
    /**
     * Select a color using a JColorChooser
     * @param parent parent frame of the window
     * @param brush brush or background color selection
     * @param previous previously used (or default) color
     * @return selected color or the previously used color if the operation canceled
     */
    public static Color chooseColor(JFrame parent, boolean brush, Color previous) {
        Color c =  JColorChooser.showDialog(parent,
                brush ? "Select a Brush color" : "Select a Background color",
                previous,
                false);

        if (c == null) return previous;
        return c;
    }
}
