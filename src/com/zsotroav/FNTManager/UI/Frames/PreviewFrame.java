package com.zsotroav.FNTManager.UI.Frames;

import com.zsotroav.FNTManager.Font.Font;
import com.zsotroav.FNTManager.Font.Symbol;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.zsotroav.FNTManager.Common.chooseColor;

public class PreviewFrame extends JFrame {
    private Font font;
    private JTextField textField;
    private JScrollPane scrollPane;
    private BufferedImage img;
    private JLabel imageLabel;

    private int spacing = 1;
    private int scale = 15;

    private Color brushColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;

    public PreviewFrame(Font f) {
        super("Preview font");
        font = f;

        this.setLayout(new BorderLayout());

        img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        imageLabel = new JLabel();

        scrollPane = new JScrollPane(imageLabel);
        scrollPane.setBackground(Color.PINK);
        scrollPane.setPreferredSize(new Dimension(400, 200));


        this.add(scrollPane, BorderLayout.NORTH);

        textField = new JTextField("");
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { textChanged(); }
            @Override public void changedUpdate(DocumentEvent e)  { textChanged(); }
            @Override public void removeUpdate(DocumentEvent e)  { textChanged(); }
        });
        this.add(textField, BorderLayout.SOUTH);

        createMenu();

        this.setSize(700, 280);
        this.setVisible(true);
    }

    public void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu viewMenu = new JMenu("View");
        JMenuItem viewBrushItem = new JMenuItem("Change Brush Color");
        viewBrushItem.addActionListener(e ->
                brushColor = chooseColor(this, true, brushColor));
        viewMenu.add(viewBrushItem);
        JMenuItem viewBackgroundItem = new JMenuItem("Change Background Color");
        viewBackgroundItem.addActionListener(e ->
                backgroundColor = chooseColor(this, false, backgroundColor));
        viewMenu.add(viewBackgroundItem);
        JMenuItem viewScaleItem = new JMenuItem("Change Scale");
        viewScaleItem.addActionListener(e ->{
            try {
                int i = Integer.parseInt(JOptionPane.showInputDialog("Enter the new scale: "));
                if (i > 0 && i < 100) scale = i;
                textChanged();
            } catch (NumberFormatException ignored) {}
        });
        viewMenu.add(viewScaleItem);
        JMenuItem viewSpaceItem = new JMenuItem("Change Spacing");
        viewSpaceItem.addActionListener(e ->{
            try {
                int i = Integer.parseInt(JOptionPane.showInputDialog("Enter the new spacing: "));
                if (i > 0 && i < 100) spacing = i;
                textChanged();
            } catch (NumberFormatException ignored) {}
        });
        viewMenu.add(viewSpaceItem);
        menuBar.add(viewMenu);

        JMenu exportMenu = new JMenu("Export");
        JMenuItem exportImageItem = new JMenuItem("PNG");
        exportImageItem.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
                int res = fileChooser.showSaveDialog(this);
                if (res != JFileChooser.APPROVE_OPTION) return;

                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".png")) path += ".png";

                ImageIO.write(img, "png", new File(path));
                JOptionPane.showMessageDialog(this,
                        "Image Saved!", "Preview exported", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        exportMenu.add(exportImageItem);
        menuBar.add(exportMenu);

        this.setJMenuBar(menuBar);
    }

    private void createIcon() {
        ImageIcon icon = new ImageIcon(img);
        imageLabel.setIcon(icon);
        imageLabel.setBounds(0, 0, img.getWidth(), img.getHeight());
        this.repaint();
    }

    private void textChanged() {
        if (textField.getText().isEmpty()) { img = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB); return; }

        String txt = textField.getText();

        int w = 0;
        for (char c : txt.toCharArray()) {
            if (!font.containsSymbol(c)) {
                JOptionPane.showMessageDialog(this,
                    "Character missing from font: " + c, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            w += font.getCharacter(c).getWidth() + spacing;
        }

        img = new BufferedImage(w*scale, font.getHeight()*scale, BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = img.getGraphics();
        gfx.setColor(backgroundColor);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());

        w = 0;
        for (char c : txt.toCharArray()) {
            Symbol s = font.getCharacter(c);
            for (int i = 0; i < s.getWidth(); i++) {
                for (int j = 0; j < s.getHeight(); j++) {
                    gfx.setColor(s.getPixel(i,j) ? brushColor : backgroundColor);
                    gfx.fillRect((i + w)*scale, j*scale, scale, scale);
                }
            }
            w += s.getWidth() + spacing;
        }
        createIcon();
    }
}
