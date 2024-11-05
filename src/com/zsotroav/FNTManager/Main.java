package com.zsotroav.FNTManager;

import com.zsotroav.FNTManager.Font.Font;
import com.formdev.flatlaf.FlatDarkLaf;
import com.zsotroav.FNTManager.File.Exporter.FontExporter;
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

import static com.zsotroav.FNTManager.Common.chooseColor;


public class Main {
    private static MainView mainView;
    private static MenuBar menuBar;

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        JFrame frame = new JFrame("FNTManager");
        frame.setSize(new Dimension(600, 400));
        menuBar = new MenuBar();
        menuBar.setEnabled(false, false);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainView = new MainView();
        frame.add(mainView);

        ///////////////////////////////////////////////////
        // Events

        ////////////////
        // Font
        menuBar.newFontItem.addActionListener(e -> {
            try {
                var multi = new MultiInputDialog("Font ID: ", "Symbol heights:");
                if (!multi.show("Create new Font")) return;

                Font f = new Font(Integer.parseInt(multi.getB()));
                f.addSymbol(new Symbol(' ', 2, f.getHeight()));
                frame.remove(mainView);
                mainView = new MainView(f);
                frame.add(mainView);
                mainView.updateUI();
                menuBar.setEnabled(true);
                mainView.addEditSaveActionListener(ee -> menuBar.setEnabled(mainView.inEditMode()));
            } catch (Exception ignored) {}
        });

        menuBar.closeFontItem.addActionListener(e -> {
            try {
                var op = JOptionPane.showConfirmDialog(frame,
                        "Are you sure you want to close the font?\n" +
                                "Make sure it was saved properly.",
                        "Close font",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (op != JOptionPane.YES_OPTION) return;

                frame.remove(mainView);
                mainView = new MainView();
                frame.add(mainView);
                mainView.updateUI();
                menuBar.setEnabled(false, false);
            } catch (Exception ignored) {}
        });

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

        menuBar.editDeleteItem.addActionListener(e -> {
            try {
                if (mainView.font.size() <= 1) {
                    JOptionPane.showConfirmDialog(frame, "Font must contain at least one symbol!",
                            "Error deleting symbol", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                mainView.removeSelected();
                mainView.updateUI();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        ////////////////
        // View
        menuBar.viewBrushItem.addActionListener(e ->
                mainView.setBrushColor(chooseColor(frame, true, mainView.getBrushColor())));
        menuBar.viewBackgroundItem.addActionListener(e ->
                mainView.setBackgroundColor(chooseColor(frame, false, mainView.getBackgroundColor())));
        menuBar.viewScaleItem.addActionListener(e -> {
            try {
                int scale = Integer.parseInt(JOptionPane.showInputDialog("Enter the new scale: "));
                if (scale > 0 && scale < 100) mainView.changePreviewScale(scale);
            } catch (NumberFormatException ignored) {}
        });

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
            menuBar.setEnabled(true);
            mainView.addEditSaveActionListener(ee -> menuBar.setEnabled(mainView.inEditMode()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                exporter.exportFont(mainView.font, file);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static char transformInput(String s) {
        if (s.length() > 2 && s.startsWith("0x")) return BitTurmix.byteIntToUTF8(Integer.decode(s));
        return s.charAt(0);
    }
}
