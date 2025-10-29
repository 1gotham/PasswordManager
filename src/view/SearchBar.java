package view;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class SearchBar extends JPanel {
    private JTextField searchField;

    public SearchBar() {
        this.setLayout(new BorderLayout());
        searchField = new JTextField(20);
        this.add(new JLabel("Search: "), BorderLayout.WEST);
        this.add(searchField, BorderLayout.CENTER);
    }

    public void searchListener(DocumentListener listener) {
        searchField.getDocument().addDocumentListener(listener);
    }

    public String getSearchText() {
        return searchField.getText();
    }
}
