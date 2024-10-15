package com.zsotroav.FNTReader;

import com.zsotroav.FNTReader.UI.*;
import com.zsotroav.FNTReader.Font.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new JLabel("asd"));
        frame.setVisible(true);

        JDialog f = new JDialog(frame, "Character editor");
        f.setResizable(false);

        boolean[][] arr = {
                {true, true, false},
                {false, true, true },
                {false, true, true },
                {false, true, true },
        };

        Symbol s = new Symbol(0xABCD, arr);

        var p = new PixelJPanel(s.getPixels(), 50);
        //var p = new PixelJPanel(4, 7, 40);

        f.add(p);
        f.pack();
        f.setVisible(true);

        System.out.println("hello");

    }
}
