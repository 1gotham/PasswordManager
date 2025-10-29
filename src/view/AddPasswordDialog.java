package view;

import model.EntryModel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class AddPasswordDialog extends JDialog {
    private JTextField tfName;
    private JTextField tfLogin;
    private JTextField tfPassword;
    private JTextField tfCategory;
    private EntryModel result;
    private JButton generatePassBtn;

    public AddPasswordDialog(JFrame owner, EntryModel entry) {
        super(owner, "Edit entry", true);
        this.setLayout(new BorderLayout());
        this.setSize(600, 300);
        this.setLocationRelativeTo(owner);

        result = null;

        tfName = new JTextField();
        tfLogin = new JTextField();
        tfPassword = new JTextField();
        generatePassBtn = new JButton("Generate");
        tfCategory = new JTextField();

        JPanel fields = new JPanel(new GridLayout(4, 2, 3, 3));
        fields.add(new JLabel("Name:"));
        fields.add(tfName);
        fields.add(new JLabel("Login:"));
        fields.add(tfLogin);
        fields.add(new JLabel("Password:"));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(tfPassword, BorderLayout.CENTER);
        passwordPanel.add(generatePassBtn, BorderLayout.EAST);
        fields.add(passwordPanel);

        fields.add(new JLabel("Category:"));
        fields.add(tfCategory);

        tfName.setText(entry.getName());
        tfLogin.setText(entry.getLogin());
        tfPassword.setText(entry.getPassword());
        tfCategory.setText(entry.getCategory());
        this.add(fields, BorderLayout.CENTER);

        JPanel btnsPanel = new JPanel();
        JButton btnOk = new JButton("Edit");
        JButton btnCancel = new JButton("Cancel");
        btnsPanel.add(btnOk);
        btnsPanel.add(btnCancel);
        this.add(btnsPanel, BorderLayout.SOUTH);

        generatePassBtn.addActionListener(e -> {
            tfPassword.setText(generateRandomPassword());
        });

        btnCancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });
        btnOk.addActionListener(e -> {
            if (tfName.getText().isBlank() ||
                    tfLogin.getText().isBlank() ||
                    tfPassword.getText().isBlank() ||
                    tfCategory.getText().isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Adding error! Fields are blank. Check if you filled every field.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            result = new EntryModel();
            result.setName(tfName.getText());
            result.setLogin(tfLogin.getText());
            result.setPassword(tfPassword.getText());
            result.setCategory(tfCategory.getText());
            setVisible(false);
        });
    }

    public AddPasswordDialog(JFrame owner) {
        super(owner, "Add new password", true);
        this.setLayout(new BorderLayout());
        this.setSize(600, 300);
        this.setLocationRelativeTo(owner);

        result = null;

        tfName = new JTextField();
        tfLogin = new JTextField();
        tfPassword = new JTextField();
        generatePassBtn = new JButton("Generate");
        tfCategory = new JTextField();

        JPanel fields = new JPanel(new GridLayout(4, 2, 3, 3));
        fields.add(new JLabel("Name:"));
        fields.add(tfName);
        fields.add(new JLabel("Login:"));
        fields.add(tfLogin);
        fields.add(new JLabel("Password:"));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(tfPassword, BorderLayout.CENTER);
        passwordPanel.add(generatePassBtn, BorderLayout.EAST);
        fields.add(passwordPanel);

        fields.add(new JLabel("Category:"));
        fields.add(tfCategory);
        this.add(fields, BorderLayout.CENTER);

        JPanel btnsPanel = new JPanel();
        JButton btnOk = new JButton("Add");
        JButton btnCancel = new JButton("Cancel");
        btnsPanel.add(btnOk);
        btnsPanel.add(btnCancel);
        this.add(btnsPanel, BorderLayout.SOUTH);

        generatePassBtn.addActionListener(e -> {
            tfPassword.setText(generateRandomPassword());
        });

        btnCancel.addActionListener(e -> {
            result = null;
            setVisible(false);
        });
        btnOk.addActionListener(e -> {
            if (tfName.getText().isBlank() ||
                    tfLogin.getText().isBlank() ||
                    tfPassword.getText().isBlank() ||
                    tfCategory.getText().isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Adding error! Fields are blank. Check if you filled every field.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            result = new EntryModel();
            result.setName(tfName.getText());
            result.setLogin(tfLogin.getText());
            result.setPassword(tfPassword.getText());
            result.setCategory(tfCategory.getText());
            setVisible(false);
        });
    }

    public EntryModel getResult() {
        return result;
    }

    // https://programistajava.pl/losowosc-w-javie-klasa-random/
    private String generateRandomPassword() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!&$%";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int a = random.nextInt(alphabet.length());
            sb.append(alphabet.charAt(a));
        }
        return sb.toString();
    }
}
