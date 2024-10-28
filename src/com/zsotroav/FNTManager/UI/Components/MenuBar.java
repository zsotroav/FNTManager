package com.zsotroav.FNTManager.UI.Components;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public JMenu newMenu, importMenu, exportMenu, editMenu, viewMenu, aboutMenu;

    public JMenuItem exportFNTItem, exportFontStripItem,
                     editNewItem, editCharItem, editWidthItem, editDeleteItem,
                     viewBrushItem, viewBackgroundItem, viewScaleItem;

    public MenuBar() {
        super();

        newMenu = new JMenu("New font");
        this.add(newMenu);

        importMenu = new JMenu("Import");
        this.add(importMenu);

        exportMenu = new JMenu("Export");
        exportFNTItem = new JMenuItem("FNT File");
        exportMenu.add(exportFNTItem);
        exportFontStripItem = new JMenuItem("Font Strip Image");
        exportMenu.add(exportFontStripItem);
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
        this.add(aboutMenu);
    }
}
