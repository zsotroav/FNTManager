package com.zsotroav.FNTManager;

import com.zsotroav.FNTManager.File.Importer.FNTImporter;
import com.zsotroav.FNTManager.UI.*;
import com.zsotroav.FNTManager.Font.*;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Main {

    private static ArrayList<Symbol> listData;
    private static SelectJList list;

    private static PixelJPanel pixelPanel = new PixelJPanel(1,1,50);

    public static class SelectUpdated implements ListSelectionListener
    {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            System.out.println(e.getLastIndex());
            pixelPanel.setImg(listData.get(list.getSelectedIndex()).getPixels());
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        Font f = null;
        try {
            f = new FNTImporter().importFont("example.fnt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        listData = new ArrayList<>();

        JFrame frame = new JFrame();

        listData.addAll(f.getSymbols());

        list = new SelectJList(listData, new SelectUpdated());

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                           list, pixelPanel);
        sp.setOneTouchExpandable(true);
        sp.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane

        frame.add(sp);
        frame.pack();
        frame.setVisible(true);

        System.out.println("hello");

    }
}
