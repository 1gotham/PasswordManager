package view;

import model.EntryModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MasterPassDialog extends JDialog {
    private JPasswordField passField;
    String result;

    public MasterPassDialog(JFrame owner) {
        super(owner, "Enter master password", true);
        this.setLayout(new BorderLayout());
        this.setSize(300, 100);
        this.setLocationRelativeTo(owner);

        passField = new JPasswordField();
        passField.setEditable(true);
        this.add(passField);

        JPanel btnsPanel = new JPanel();
        JButton btnOk = new JButton("Ok");
        JButton btnCancel = new JButton("Cancel");
        btnsPanel.add(btnOk);
        btnsPanel.add(btnCancel);
        this.add(btnsPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });
        btnOk.addActionListener(e -> {
            if (Arrays.toString(passField.getPassword()).isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Adding error! Field is blank. Check if you filled the field.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            result = new String(passField.getPassword());
            setVisible(false);
        });

        this.setVisible(true);
    }

    public String getResult() {
        return result;
    }
}
