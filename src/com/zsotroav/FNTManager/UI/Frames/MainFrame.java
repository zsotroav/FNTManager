package com.zsotroav.FNTManager.UI.Frames;

import com.zsotroav.FNTManager.File.Exporter.FontExporter;
import com.zsotroav.FNTManager.File.Importer.FontImporter;
import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.Symbol;
import com.zsotroav.FNTManager.UI.Components.MenuBar;
import com.zsotroav.FNTManager.UI.Views.MainView;
import com.zsotroav.FNTManager.UI.Views.MultiInputDialog;
import com.zsotroav.Util.BitTurmix;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

import static com.zsotroav.FNTManager.Common.*;

/**
 * The main application JFrame 
 */
public class MainFrame extends JFrame {
    private MainView mainView;
    private MenuBar menuBar;

    /**
     * Init the frame with a loaded font
     * @param f font to use
     */
    public MainFrame(Font f) {
        this();
        remove(mainView);
        mainView = new MainView(f);
        add(mainView);
        mainView.updateUI();
        menuBar.setEnabled(true);
        mainView.addEditSaveActionListener(e -> menuBar.setEnabled(mainView.inEditMode()));
    }

    /**
     * Init the frame without a pre-loaded font
     */
    public MainFrame() {
        super("FNTManager");
        setSize(new Dimension(600, 400));
        menuBar = new MenuBar();
        menuBar.setEnabled(false, false);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImages(getIcons(getClass()));

        mainView = new MainView();
        add(mainView);

        ///////////////////////////////////////////////////
        // Events

        ////////////////
        // Font
        menuBar.newFontItem.addActionListener(e -> {
            try {
                var multi = new MultiInputDialog("Font ID: ", "Symbol heights:");
                if (!multi.show("Create new Font")) return;

                com.zsotroav.FNTManager.Font.Font f = new Font(Integer.parseInt(multi.getB()));
                f.addSymbol(new Symbol(' ', 2, f.getHeight()));
                remove(mainView);
                mainView = new MainView(f);
                add(mainView);
                mainView.updateUI();
                menuBar.setEnabled(true);
                mainView.addEditSaveActionListener(ee -> menuBar.setEnabled(mainView.inEditMode()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        menuBar.closeFontItem.addActionListener(e -> {
            try {
                var op = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to close the font?\n" +
                                "Make sure it was saved properly.",
                        "Close font",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (op != JOptionPane.YES_OPTION) return;

                this.remove(mainView);
                mainView = new MainView();
                this.add(mainView);
                mainView.updateUI();
                menuBar.setEnabled(false, false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ////////////////
        // Import/Export
        for (var item : menuBar.importItems)
            item.y.addActionListener(e -> importEvent(this, item.x));

        for (var item : menuBar.exportItems)
            item.y.addActionListener(e -> exportEvent(this, item.x));

        ////////////////
        // Edit
        menuBar.editNewItem.addActionListener(e -> {
            try {
                var multi = new MultiInputDialog("Symbol character: ", "Symbol width: ");
                if (!multi.show("Create new Symbol")) return;

                mainView.addSymbol(new Symbol(transformInput(multi.getA()),
                        Integer.parseInt(multi.getB()),
                        mainView.getFontHeight()));

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        menuBar.editCharItem.addActionListener(e -> {
            try {
                boolean s = mainView.mvSymbol(mainView.getSelectedSymbol().getCharacter(),
                        transformInput(JOptionPane.showInputDialog("Enter the new character: ")));
                if (!s) JOptionPane.showMessageDialog(this, "Failed to move symbol", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        menuBar.editWidthItem.addActionListener(e -> {
            try {
                int width = Integer.parseInt(JOptionPane.showInputDialog("Enter the new width: "));
                Symbol curr = mainView.getSelectedSymbol();
                mainView.font.removeSymbol(curr);
                curr.changeWidth(width);
                mainView.font.addSymbol(curr);
                mainView.reDraw();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        menuBar.editDeleteItem.addActionListener(e -> {
            try {
                if (mainView.font.size() <= 1) {
                    JOptionPane.showConfirmDialog(this, "Font must contain at least one symbol!",
                            "Error deleting symbol", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                mainView.removeSelected();
                mainView.updateUI();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        ////////////////
        // View
        menuBar.viewBrushItem.addActionListener(e ->
                mainView.setBrushColor(chooseColor(this, true, mainView.getBrushColor())));
        menuBar.viewBackgroundItem.addActionListener(e ->
                mainView.setBackgroundColor(chooseColor(this, false, mainView.getBackgroundColor())));
        menuBar.viewScaleItem.addActionListener(e -> {
            try {
                int scale = Integer.parseInt(JOptionPane.showInputDialog("Enter the new scale: "));
                if (scale > 0 && scale < 100) mainView.changePreviewScale(scale);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Handle importing of a font
     * @param frame parent frame for the JFileChooser
     * @param importer the FontImporter to use
     */
    private void importEvent(JFrame frame, FontImporter importer) {
        try {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            String[] par = importer.getFileNameExtensionFormat().split(";");

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

    /**
     * Handle exporting of a font
     * @param frame parent frame for the JFileChooser
     * @param exporter the FontExporter to use
     */
    private void exportEvent(JFrame frame, FontExporter exporter) {
        try {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            String[] par = exporter.getFileNameExtensionFormat().split(";");

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

    /**
     * Transform the string input for character selection
     * @param s Character string in either Unicode Code Points (prefixed with 0x) or character literals
     * @return parsed character
     */
    private static char transformInput(String s) {
        if (s.length() > 2 && s.startsWith("0x")) return BitTurmix.byteIntToUTF8(Integer.decode(s));
        return s.charAt(0);
    }
}


