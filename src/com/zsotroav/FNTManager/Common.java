package com.zsotroav.FNTManager;

import javax.swing.*;
import java.awt.*;

public class Common {
    public static Color chooseColor(JFrame parent, boolean brush, Color previous) {
        Color c =  JColorChooser.showDialog(parent,
                brush ? "Select a Brush color" : "Select a Background color",
                previous,
                false);

        if (c == null) return previous;
        return c;
    }
}
