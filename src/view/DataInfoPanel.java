package view;

import javax.swing.*;

public class DataInfoPanel extends JPanel{
    JLabel infoLabel;
    public DataInfoPanel() {
        infoLabel = new JLabel();
        this.add(infoLabel);
    }

    public JLabel getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(String text) {
        infoLabel.setText(text);
    }
}
