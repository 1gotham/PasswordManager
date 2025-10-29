package view;

import javax.swing.*;
import java.awt.*;

public class InitDialog extends JDialog {
    JButton btnNewFile;
    JButton btnOpenFile;

    public InitDialog(JFrame owner) {
        super(owner, "Choose option", true);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setSize(300, 125);
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
        btnNewFile = new JButton("New File");
        btnOpenFile = new JButton("Open file");
        JPanel btnsPanel = new JPanel();
        btnsPanel.add(btnNewFile);
        btnsPanel.add(btnOpenFile);
        this.getContentPane().add(btnsPanel);
        this.pack();
    }

    public JButton getBtnNewFile() {
        return btnNewFile;
    }

    public JButton getBtnOpenFile() {
        return btnOpenFile;
    }
}
