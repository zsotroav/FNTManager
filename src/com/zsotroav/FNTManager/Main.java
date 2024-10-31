package com.zsotroav.FNTManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.zsotroav.FNTManager.File.Exporter.FontExporter;
import com.zsotroav.FNTManager.File.Importer.FNTImporter;
import com.zsotroav.FNTManager.File.Importer.FontImporter;
import com.zsotroav.FNTManager.Font.Symbol;
import com.zsotroav.FNTManager.UI.Components.MenuBar;
import com.zsotroav.FNTManager.UI.Views.*;
import com.zsotroav.Util.BitTurmix;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;


public class Main {
    private static MainView mainView;
    private static MenuBar menuBar;

    public static void main(String[] args) {
        FlatDarkLaf.setup();
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
        // Import/Export
        for (var item : menuBar.importItems)
            item.y.addActionListener(e -> importEvent(frame, e, item.x));

        for (var item : menuBar.exportItems)
            item.y.addActionListener(e -> exportEvent(frame, e, item.x));

        ////////////////
        // Edit
        menuBar.editNewItem.addActionListener(e -> {
            try {
                var multi = new MultiInputDialog("Symbol character: ", "Symbol width: ");
                if (!multi.show("Create new Symbol")) return;

                mainView.addSymbol(new Symbol(transformInput(multi.getA()),
                                   Integer.parseInt(multi.getB()),
                                   mainView.getFontHeight()));

            } catch (Exception ignored) {}
        });

        menuBar.editCharItem.addActionListener(e -> {
            try {
                mainView.mvSymbol(mainView.getSelectedSymbol().getCharacter(),
                        transformInput(JOptionPane.showInputDialog("Enter the new character: ")));
            } catch (Exception ignored) {}
        });

        menuBar.editWidthItem.addActionListener(e -> {
            try {
                int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the new width: "));
                Symbol curr = mainView.getSelectedSymbol();
                mainView.font.removeCharacter(curr);
                curr.changeWidth(width);
                mainView.font.addSymbol(curr);
                mainView.reDraw();
            } catch (Exception ignored) { }
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

    private static void importEvent(JFrame frame, ActionEvent e, FontImporter importer) {
        try {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            String[] par = e.getActionCommand().split(";");

            fileChooser.setFileFilter(new FileNameExtensionFilter(par[0], par[1].split("!")));
            int res = fileChooser.showOpenDialog(frame);
            if (res == JFileChooser.APPROVE_OPTION) {
                String file = fileChooser.getSelectedFile().getAbsolutePath();
                frame.remove(mainView);
                mainView = new MainView(importer.importFont(file));
                frame.add(mainView);
                mainView.updateUI();
            }
        } catch (Exception ignored) { }
    }

    private static void exportEvent(JFrame frame, ActionEvent e, FontExporter exporter) {
        try {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            String[] par = e.getActionCommand().split(";");

            fileChooser.setFileFilter(new FileNameExtensionFilter(par[0], par[1].split("!")));
            int res = fileChooser.showSaveDialog(frame);
            if (res == JFileChooser.APPROVE_OPTION) {
                String file = fileChooser.getSelectedFile().getAbsolutePath();
                exporter.exportFont(file);
            }
        } catch (Exception ignored) { }
    }

    private static char transformInput(String s) {
        if (s.length() > 2 && s.startsWith("0x")) return BitTurmix.byteIntToUTF8(Integer.decode(s));
        return s.charAt(0);
    }

    private static void chooseColor(JFrame parent, boolean brush) {
        Color c =  JColorChooser.showDialog(parent,
                        brush ? "Select a Brush color" : "Select a Background color",
                        brush ? mainView.getBrushColor() : mainView.getBackgroundColor(),
                        false);

        if (brush) mainView.setBrushColor(c);
        else       mainView.setBackgroundColor(c);
    }
}
