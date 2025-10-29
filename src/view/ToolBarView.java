package view;

import javax.swing.*;

public class ToolBarView extends JToolBar {
    JButton btnAdd;
    JButton btnSave;
    JButton btnOpen;
    JComboBox comboBoxSort;

    public JComboBox getComboBoxSort() {
        return comboBoxSort;
    }

    public ToolBarView() {
        this.setFloatable(false);
        btnAdd = new JButton("Add");
        btnSave = new JButton("Save");
        btnOpen = new JButton("Open");

        String[] test = { "ID", "NAME", "LOGIN" };
        JLabel sortLabel = new JLabel("Sort");
        comboBoxSort = new JComboBox(test);
        JPanel combos = new JPanel();
        combos.add(sortLabel);
        combos.add(comboBoxSort);

        this.add(combos);
        this.add(btnAdd);
        this.addSeparator();
        this.add(btnSave);
        this.addSeparator();
        this.add(btnOpen);
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnOpen() {
        return btnOpen;
    }
}
