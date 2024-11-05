package com.zsotroav.FNTManager.UI.Components;

import com.zsotroav.FNTManager.File.Exporter.FontExporter;
import com.zsotroav.FNTManager.File.Importer.FontImporter;
import com.zsotroav.FNTManager.UI.Frames.AboutFrame;
import com.zsotroav.Util.Tuple;

import javax.swing.*;
import java.util.ArrayList;
import java.util.ServiceLoader;

public class MenuBar extends JMenuBar {
    private JMenu fontMenu, importMenu, exportMenu, editMenu, viewMenu, aboutMenu;

    public JMenuItem newFontItem, closeFontItem,
                     editNewItem, editCharItem, editWidthItem, editDeleteItem,
                     viewBrushItem, viewBackgroundItem, viewScaleItem,
                     aboutFNTManagerItem;

    public ArrayList<Tuple<FontImporter, JMenuItem>> importItems;
    public ArrayList<Tuple<FontExporter, JMenuItem>> exportItems;

    public void setEnabled(boolean b) { setEnabled(b, true); }

    public void setEnabled(boolean b, boolean all) {
        exportMenu.setEnabled(b);
        editMenu.setEnabled(b);
        viewMenu.setEnabled(b);

        if (all) {
            fontMenu.setEnabled(b);
            importMenu.setEnabled(b);
            aboutMenu.setEnabled(b);
        }
    }

    public MenuBar() {
        super();

        importItems = new ArrayList<>();
        exportItems = new ArrayList<>();

        fontMenu = new JMenu("Font");
        newFontItem = new JMenuItem("New Font");
        fontMenu.add(newFontItem);
        closeFontItem = new JMenuItem("Close Font");
        fontMenu.add(closeFontItem);
        this.add(fontMenu);

        importMenu = new JMenu("Import");

        for (FontImporter implClass : ServiceLoader.load(FontImporter.class)) {
            JMenuItem importItem = new JMenuItem();
            importItem.setText(implClass.getUserFriendlyName());
            importItem.setActionCommand(implClass.getFileNameExtensionFormat());

            importItems.add(new Tuple<>(implClass, importItem));
            importMenu.add(importItem);
        }
        this.add(importMenu);

        exportMenu = new JMenu("Export");
        for (FontExporter implClass : ServiceLoader.load(FontExporter.class)) {
            JMenuItem exportItem = new JMenuItem();
            exportItem.setText(implClass.getUserFriendlyName());
            exportItem.setActionCommand(implClass.getFileNameExtensionFormat());

            exportItems.add(new Tuple<>(implClass, exportItem));
            exportMenu.add(exportItem);
        }
        this.add(exportMenu);

        editMenu = new JMenu("Edit");
        editNewItem = new JMenuItem("Create New Symbol");
        editMenu.add(editNewItem);
        editCharItem = new JMenuItem("Change Symbol Character");
        editMenu.add(editCharItem);
        editWidthItem = new JMenuItem("Change Symbol Width");
        editMenu.add(editWidthItem);
        editDeleteItem = new JMenuItem("Delete Symbol from Font");
        editMenu.add(editDeleteItem);
        this.add(editMenu);

        viewMenu = new JMenu("View");
        viewBrushItem = new JMenuItem("Change Brush Color");
        viewMenu.add(viewBrushItem);
        viewBackgroundItem = new JMenuItem("Change Background Color");
        viewMenu.add(viewBackgroundItem);
        viewScaleItem = new JMenuItem("Change Preview Scale");
        viewMenu.add(viewScaleItem);
        this.add(viewMenu);

        aboutMenu = new JMenu("About");
        aboutFNTManagerItem = new JMenuItem("FNTManager");
        aboutFNTManagerItem.addActionListener( l -> new AboutFrame() );
        aboutMenu.add(aboutFNTManagerItem);
        this.add(aboutMenu);
    }
}
