package com.zsotroav.FNTManager.UI.Views;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.*;
import com.zsotroav.FNTManager.UI.Components.*;
import com.zsotroav.FNTManager.UI.Frames.PreviewFrame;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainView extends JPanel {
    public Font font;
    private SelectJList list;

    private JLabel rightLabel;
    private JButton editSaveButton;
    private JButton previewButton;
    private PixelJPanel pixelPanel = new PixelJPanel(1, 1);

    public Symbol getSelectedSymbol() { return font.getSymbol(list.getSelected()); }

    private class ListSelectUpdated implements ListSelectionListener {
        @Override public void valueChanged(ListSelectionEvent e) { reDraw(); }
    }

    public void reDraw() {
        pixelPanel.setImg(getSelectedSymbol().getPixels());
        rightLabel.setText("Selected Symbol: " + getSelectedSymbol());
    }

    public void addEditSaveActionListener(ActionListener l) { editSaveButton.addActionListener(l); }
    public boolean inEditMode() { return !pixelPanel.isReadOnly(); }

    public MainView() {
        JLabel label = new JLabel("No font loaded");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.setLayout(new BorderLayout());
        this.add(label, BorderLayout.CENTER);
    }

    private JPanel leftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setMinimumSize(new Dimension(150,100));
        leftPanel.setPreferredSize(new Dimension(200,400));

        JLabel leftLabel = new JLabel("Available Symbols:");
        leftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(leftLabel, BorderLayout.NORTH);

        list = new SelectJList(font.getSymbols(), new ListSelectUpdated());
        leftPanel.add(list, BorderLayout.CENTER);

        previewButton = new JButton("Preview Font");
        previewButton.setHorizontalAlignment(SwingConstants.CENTER);
        previewButton.addActionListener(l -> new PreviewFrame(font));

        leftPanel.add(previewButton, BorderLayout.SOUTH);

        return leftPanel;
    }

    private JPanel rightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setMinimumSize(new Dimension(200,150));
        rightPanel.setPreferredSize(new Dimension(400,400));

        rightLabel = new JLabel("Selected Symbol:");
        rightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(rightLabel, BorderLayout.NORTH);

        rightPanel.add(pixelPanel, BorderLayout.CENTER);

        editSaveButton = new JButton("Edit Symbol");
        editSaveButton.setHorizontalAlignment(SwingConstants.CENTER);

        editSaveButton.addActionListener(e -> {
            if (pixelPanel.isReadOnly()) {
                editSaveButton.setText("Save Symbol");
                pixelPanel.setReadOnly(false);
                previewButton.setEnabled(false);
                list.setEnabled(false);
            } else {
                editSaveButton.setText("Edit Symbol");
                pixelPanel.setReadOnly(true);
                list.setEnabled(true);
                previewButton.setEnabled(true);

                Symbol s = font.getSymbol(list.getSelected());
                font.removeSymbol(list.getSelected());
                s.setPixels(pixelPanel.getData());
                font.addSymbol(s);
            }
        });

        rightPanel.add(editSaveButton, BorderLayout.SOUTH);
        return rightPanel;
    }

    public MainView(Font f) {
        font = f;
        this.setLayout(new GridLayout(1,1));

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel(), rightPanel());
        sp.setDividerLocation(200);
        this.add(sp);

        reDraw();
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
        Symbol old = font.getSymbol(from);
        font.removeSymbol(old);
        old.setCharacter(to);
        font.addSymbol(old);

        list.replace(from, to);
        list.updateUI();
    }
    public void removeSelected() {
        font.removeSymbol(list.getSelected());
        list.removeSelected();
    }
}
