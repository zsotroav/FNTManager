package com.zsotroav.FNTManager;

import com.zsotroav.FNTManager.File.Importer.FNTImporter;
import com.zsotroav.FNTManager.Font.Symbol;
import com.zsotroav.FNTManager.UI.Components.MenuBar;
import com.zsotroav.FNTManager.UI.Views.*;
import com.zsotroav.Util.BitTurmix;

import javax.swing.*;
import java.awt.*;


public class Main {
    private static MainView mainView;
    private static MenuBar menuBar;

    public static void main(String[] args) {
        JFrame frame = new JFrame("FNTManager");
        menuBar = new MenuBar();
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            mainView = new MainView(new FNTImporter().importFont("example.fnt"));
            frame.add(mainView);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        ///////////////////////////////////////////////////
        // Events


        ////////////////
        // Edit
        menuBar.editNewItem.addActionListener(e -> {
            try {
                var multi = new MultiInputDialog("Symbol character: ", "Symbol width: ");
                if (!multi.show("Create new Symbol")) return;

                String chr = multi.getA();
                if (chr.length() > 2 && chr.startsWith("0x")) {
                    mainView.addSymbol(new Symbol(BitTurmix.byteIntToUTF8(Integer.decode(chr)),
                                       Integer.parseInt(multi.getB()),
                                       mainView.getFontHeight()));
                } else if (chr.length() == 1){
                    mainView.addSymbol(new Symbol(chr.toCharArray()[0],
                                       Integer.parseInt(multi.getB()),
                                       mainView.getFontHeight()));
                }

            } catch (Exception ignored) {}
        });


        ////////////////
        // View
        menuBar.viewBrushItem.addActionListener(e -> chooseColor(frame, true));
        menuBar.viewBackgroundItem.addActionListener(e -> chooseColor(frame, false));
        menuBar.viewScaleItem.addActionListener(e -> {
            try {
                int scale = Integer.parseInt(JOptionPane.showInputDialog("Enter the new scale: "));
                if (scale > 0 && scale < 100) mainView.changePreviewScale(scale);
            } catch (NumberFormatException ignored) {}
        });

        frame.pack();
        frame.setVisible(true);
    }

    public static void chooseColor(JFrame parent, boolean brush) {
        Color c =  JColorChooser.showDialog(parent,
                        brush ? "Select a Brush color" : "Select a Background color",
                        brush ? mainView.getBrushColor() : mainView.getBackgroundColor(),
                        false);

        if (brush) mainView.setBrushColor(c);
        else       mainView.setBackgroundColor(c);
    }
}
