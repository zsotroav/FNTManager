package com.zsotroav.FNTManager.UI.Components;

import com.zsotroav.FNTManager.Font.Symbol;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Scrollable click-to-select JList
 */
public class SelectJList<T extends Comparable<T>> extends JPanel {
    private JList<T> list;
    private DefaultListModel<T> listModel;
    private JScrollPane scrollPane;

    @Override public void setEnabled(boolean b) { list.setEnabled(b); }
    @Override public boolean isEnabled() { return list.isEnabled(); }

    /**
     * Initialize with an empty list
     */
    public SelectJList() {
        this.setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();

        list = new JList<>(listModel);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

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
        scrollPane.setMinimumSize(new Dimension(200, 100));
        scrollPane.setPreferredSize(new Dimension(300, 100));

        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Initialize with a basic list
     * @param itemList List to use
     * @param listener Selection listener to use
     */
    public SelectJList(Collection<T> itemList, ListSelectionListener listener) {
        this();
        for (T t : itemList) { addItem(t); }

        if (!itemList.isEmpty()) list.setSelectedIndex(0);

        list.addListSelectionListener(listener);
    }

    public void addItem(T c) { listModel.addElement(c); sortModel(listModel); }

    public void sortModel(DefaultListModel<T> model) {
        ArrayList<T> l = new ArrayList<>();
        for (int i = 0; i < model.size(); i++) {
            l.add(model.get(i));
        }
        Collections.sort(l);
        model.removeAllElements();
        for (T s : l) {
            model.addElement(s);
        }
    }

    public T getItem(int idx) { return listModel.getElementAt(idx); }

    /**
     * Replace the {@code to} character with the {@code from} character
     * @param from Item to be added
     * @param to The item to be replaced
     */
    public void replace(T from, T to) { listModel.set(listModel.indexOf(from), to); }

    public int getSelectedIndex() { return list.getSelectedIndex(); }
    public T getSelected() { return list.getSelectedValue(); }

    /**
     * Remove the currently selected item from the list
     */
    public void removeSelected() {
        // To not have an absolute mess with the action listeners triggering, we temp remove them
        var ac = list.getListSelectionListeners();
        for (var a : ac) list.removeListSelectionListener(a);

        listModel.removeElementAt(list.getSelectedIndex());

        for (var a : ac) list.addListSelectionListener(a);

        // Set a proper id for selection and trigger the listeners with this
        if (list.getSelectedIndex() == 0) list.setSelectedIndex(1);
        else list.setSelectedIndex(0);

    }
}
