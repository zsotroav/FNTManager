package com.zsotroav.FNTManager.UI.Views;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.*;
import com.zsotroav.FNTManager.UI.Components.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainView extends JPanel {
    private Font font;
    private SelectJList list;

    private JLabel rightLabel;
    private JButton editSaveButton;
    private JButton previewButton;
    private PixelJPanel pixelPanel = new PixelJPanel(1,1,25);

    public Symbol getSelectedSymbol() { return font.getCharacter(list.getSelected()); }

    private class listSelectUpdated implements ListSelectionListener {
        @Override public void valueChanged(ListSelectionEvent e) {
            pixelPanel.setImg(getSelectedSymbol().getPixels());
            rightLabel.setText("Selected Symbol: " + getSelectedSymbol());
        }
    }

    public MainView(Font f) {
        font = f;
        this.setLayout(new GridLayout(1,1));

        ///////////////////////////////////////////////////
        // LEFT PANEL

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));

        JLabel leftLabel = new JLabel("Available Symbols:");
        leftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(leftLabel, BorderLayout.NORTH);

        list = new SelectJList(font.getSymbols(), new listSelectUpdated());
        list.setMinimumSize(new Dimension(130,100));
        leftPanel.add(list, BorderLayout.CENTER);

        previewButton = new JButton("Preview Font");
        previewButton.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(previewButton, BorderLayout.SOUTH);

        ///////////////////////////////////////////////////
        // RIGHT PANEL

        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));

        rightLabel = new JLabel("Selected Symbol:");
        rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(rightLabel, BorderLayout.NORTH);

        pixelPanel.setMinimumSize(new Dimension(130,150));
        pixelPanel.setPreferredSize(new Dimension(150,300));
        rightPanel.add(pixelPanel, BorderLayout.CENTER);

        editSaveButton = new JButton("Edit Symbol");
        editSaveButton.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(editSaveButton, BorderLayout.SOUTH);

        ///////////////////////////////////////////////////
        // Split Pane

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        sp.setDividerLocation(130);
        this.add(sp);
    }

    public void setBrushColor(Color c) { pixelPanel.setBrushColor(c); }
    public Color getBrushColor() { return pixelPanel.getBrushColor(); }
    public void setBackgroundColor(Color c) { pixelPanel.setBackgroundColor(c); }
    public Color getBackgroundColor() { return pixelPanel.getBackgroundColor(); }
    public void changePreviewScale(int scale) { pixelPanel.setScale(scale); }

    public void addSymbol(Symbol s) {
        if (!font.addSymbol(s)) return;

        list.addItem(s);
        list.updateUI();
    }
    public int getFontHeight() { return font.getHeight(); }

    public void mvSymbol(char from, char to) {
        Symbol old = font.getCharacter(from);
        font.removeCharacter(old);
        old.setCharacter(to);
        font.addSymbol(old);

        list.replace(from, to);
        list.updateUI();
    }
}
