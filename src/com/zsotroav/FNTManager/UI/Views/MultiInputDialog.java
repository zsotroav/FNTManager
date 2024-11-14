package com.zsotroav.FNTManager.UI.Views;

import javax.swing.*;
import java.awt.*;

/**
 * JInputDialog-like dialog with two input fields
 */
public class MultiInputDialog {

    private final JTextField FieldA = new JTextField();
    private final JTextField FieldB = new JTextField();

    private final JPanel panel = new JPanel(new GridLayout(2,2));

    /**
     * Init the dialog
     * @param A Label for the first input
     * @param B Label for the second input
     */
    public MultiInputDialog(String A, String B) {
        panel.add(new JLabel(A));
        panel.add(FieldA);
        panel.add(new JLabel(B));
        panel.add(FieldB);
    }

    /**
     * Show the dialog with the given title
     * @param title Title to show
     * @return Whether the input given should be considered valid
     */
    public boolean show(String title) {
        int res = JOptionPane.showConfirmDialog(null, panel,
                  title, JOptionPane.OK_CANCEL_OPTION);
        return res == JOptionPane.OK_OPTION;
    }

    /**
     * Get the first input's value
     * @return FieldA's value
    */
    public String getA() { return FieldA.getText(); }

    /**
     * Get the second input's value
     * @return FieldB's value
     */
    public String getB() { return FieldB.getText(); }
}
