package view;

import model.DataTableModel;
import model.EntryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class DataPanel extends JPanel {
    private JTable dataTable;
    private JPopupMenu popupMenu;
    private JMenuItem deleteEntry;
    private JMenuItem editEntry;

    public interface EntryActionListener {
        void onDeleteEntry(EntryModel entry);

        void onEditEntry(EntryModel entry);
    }

    public void setActionListener(EntryActionListener actionListener) {
        ActionListener = actionListener;
    }

    private EntryActionListener ActionListener;

    public DataPanel() {
        this.setLayout(new BorderLayout());
        JLabel dataLabel = new JLabel("Data");
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(dataLabel, BorderLayout.NORTH);
        dataTable = new JTable();
        JScrollPane scroll = new JScrollPane(dataTable);
        this.add(scroll, BorderLayout.CENTER);

        popupMenu = new JPopupMenu();
        deleteEntry = new JMenuItem("Delete");
        editEntry = new JMenuItem("Edit");
        popupMenu.add(deleteEntry);
        popupMenu.add(editEntry);

        dataTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    System.out.print(dataTable.getSelectedRow());
                    int row = dataTable.rowAtPoint(e.getPoint());
                    dataTable.setRowSelectionInterval(row, row);
                    popupMenu.show(dataTable, e.getX(), e.getY());
                }
            }
        });

        editEntry.addActionListener(e -> {
            EntryModel selected = getSelectedEntry();
            ActionListener.onEditEntry(selected);
        });

        deleteEntry.addActionListener(e -> {
            EntryModel selected = getSelectedEntry();
            ActionListener.onDeleteEntry(selected);
        });
    }

    public void setPasswords(List<EntryModel> passwords) {
        DataTableModel dataTableModel = new DataTableModel(passwords);
        dataTable.setModel(dataTableModel);
    }

    public JTable getDataTable() {
        return dataTable;
    }

    public EntryModel getSelectedEntry() {
        int row = dataTable.getSelectedRow();
        return (((DataTableModel) dataTable.getModel()).getEntryAt(row));
    }
}
