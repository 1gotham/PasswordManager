package model;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DataTableModel extends AbstractTableModel {
    private final List<EntryModel> passwords;
    private final String[] columnNames = { "ID", "Name", "Password", "Login" };

    public DataTableModel(List<EntryModel> passwords) {
        this.passwords = passwords;
    }

    @Override
    public int getRowCount() {
        if (passwords != null) {
            return passwords.size();
        }
        return 1;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return switch (column) {
            case 0 -> passwords.get(row).getId();
            case 1 -> passwords.get(row).getName();
            case 2 -> passwords.get(row).getPassword();
            case 3 -> passwords.get(row).getLogin();
            default -> "";
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public EntryModel getEntryAt(int row) {
        return passwords.get(row);
    }

}
