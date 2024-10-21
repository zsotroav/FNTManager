package com.zsotroav.FNTManager;

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
        listData = new ArrayList<>();

        JFrame frame = new JFrame();

        boolean[][] arr = {
                {true, false, false},
                {true, false, false },
                {true, false, false },
                {true, false, false },
        };
        boolean[][] arr2 = {
                {false, true, false},
                {false, true, false },
                {false, true, false },
                {false, true, false },
        };
        boolean[][] arr3 = {
                {false, false, true},
                {false, false, true },
                {false, false, true },
                {false, false, true },
        };

        listData.add(new Symbol(0x0001, arr));
        listData.add(new Symbol(0xFF, arr2));
        listData.add(new Symbol(0xAA, arr3));

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
