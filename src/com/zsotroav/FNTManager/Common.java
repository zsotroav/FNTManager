package com.zsotroav.FNTManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Common {
    /**
     * Select a color using a JColorChooser
     *
     * @param parent   parent frame of the window
     * @param brush    brush or background color selection
     * @param previous previously used (or default) color
     * @return selected color or the previously used color if the operation canceled
     */
    public static Color chooseColor(JFrame parent, boolean brush, Color previous) {
        Color c = JColorChooser.showDialog(parent,
                brush ? "Select a Brush color" : "Select a Background color",
                previous,
                false);

        if (c == null) return previous;
        return c;
    }

    /**
     * Get an image from a local resource
     * @param caller Class calling (for class loader)
     * @param path path/to/image.ext
     * @return Loaded image
     */
    public static Image getResImage(Class caller, String path) {
        return Toolkit.getDefaultToolkit().getImage(caller.getResource(path));
    }

    /**
     * Get available icons in a list
     * @param caller Class calling (for class loader)
     * @return Resource loaded images
     */
    public static ArrayList<Image> getIcons(Class caller) {
        ArrayList<Image> icons = new ArrayList<>();
        icons.add(getResImage(caller, "/favicon/icon_32.png"));
        icons.add(getResImage(caller, "/favicon/icon_48.png"));
        icons.add(getResImage(caller, "/favicon/icon_64.png"));
        icons.add(getResImage(caller, "/favicon/icon_96.png"));
        icons.add(getResImage(caller, "/favicon/icon_128.png"));
        icons.add(getResImage(caller, "/favicon/icon_256.png"));
        return icons;
    }
}