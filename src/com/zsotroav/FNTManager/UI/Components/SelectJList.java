package com.zsotroav.FNTManager.UI.Components;

import com.zsotroav.FNTManager.Font.Symbol;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Collection;

public class SelectJList extends JPanel {
    private JList<Character> list;
    private DefaultListModel<Character> listModel;
    private JScrollPane scrollPane;

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
                if (renderer instanceof JLabel && value instanceof Character) {
                    ((JLabel) renderer).setText(Symbol.toString((Character) value));
                }
                return renderer;
            }
        });

        scrollPane = new JScrollPane(list);
        scrollPane.setMinimumSize(new Dimension(100, 100));
        scrollPane.setPreferredSize(new Dimension(200, 100));

        this.add(scrollPane, BorderLayout.CENTER);
    }

    public SelectJList(Collection<Symbol> itemList, ListSelectionListener listener) {
        this();
        for (Symbol symbol : itemList) { addItem(symbol.getCharacter());}

        list.addListSelectionListener(listener);
    }

    public void addItem(Symbol s) { listModel.addElement(s.getCharacter()); }
    public void addItem(char c) { listModel.addElement(c); }

    public char getItem(int idx) { return listModel.getElementAt(idx); }

    public void replace(char from, char to) { listModel.set(listModel.indexOf(from), to); }

    public int getSelectedIndex() { return list.getSelectedIndex(); }
    public char getSelected() { return list.getSelectedValue(); }

}
