package com.zsotroav.FNTManager.UI.Components;

import com.zsotroav.FNTManager.Font.Symbol;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class SelectJList extends JPanel {
    private JList<Symbol> list;
    private DefaultListModel<Symbol> listModel;
    private JScrollPane scrollPane;

    public int getSelectedIndex() { return list.getSelectedIndex(); }

    public SelectJList() {
        this.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();

        list = new JList<>(listModel);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setBackground(Color.GREEN);

        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Symbol) {
                    ((JLabel) renderer).setText((value).toString());
                }
                return renderer;
            }
        });

        scrollPane = new JScrollPane(list);
        scrollPane.setMinimumSize(new Dimension(100, 100));
        scrollPane.setPreferredSize(new Dimension(200, 100));

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public SelectJList(ArrayList<Symbol> itemList, ListSelectionListener listener) {
        this();
        listModel.addAll(itemList);

        list.addListSelectionListener(listener);
    }
}
