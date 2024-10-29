package com.zsotroav.FNTManager.UI.Views;

import javax.swing.*;
import java.awt.*;

public class MultiInputDialog {

    private JTextField FieldA = new JTextField();
    private JTextField FieldB = new JTextField();

    private JPanel panel = new JPanel(new GridLayout(2,2));

    public MultiInputDialog(String A, String B) {
        panel.add(new JLabel(A));
        panel.add(FieldA);
        panel.add(new JLabel(B));
        panel.add(FieldB);
    }

    public boolean show(String title) {
        int res = JOptionPane.showConfirmDialog(null, panel,
                  title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION;
    }

    public String getA() { return FieldA.getText(); }
    public String getB() { return FieldB.getText(); }
}
