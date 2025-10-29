package view;

import javax.swing.*;
import java.awt.*;

public class SetMasterDialog extends JDialog {
    private JTextField tfMaster;
    String result;

    public String getResult() {
        return result;
    }

    public SetMasterDialog(JFrame owner) {
        super(owner, "Set master password", true);
        this.setLayout(new BorderLayout());
        this.setSize(300, 300);
        this.setLocationRelativeTo(owner);

        tfMaster = new JTextField();
        this.add(tfMaster);

        JPanel btnsPanel = new JPanel();
        JButton btnOk = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");
        btnsPanel.add(btnOk);
        btnsPanel.add(btnCancel);
        this.add(btnsPanel, BorderLayout.SOUTH);

        btnCancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });

        btnOk.addActionListener(e -> {
            result = tfMaster.getText();
            setVisible(false);
        });

        this.setVisible(true);
    }
}
