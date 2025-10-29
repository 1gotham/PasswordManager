package view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoryPanel extends JPanel {
    private JList<String> categoryList;
    private JPopupMenu popupMenu;
    private JMenuItem deleteCat;

    // pomysl na przekierowanie do controllera
    // https://stackoverflow.com/questions/26517856/java-and-gui-where-do-actionlisteners-belong-according-to-mvc-pattern/26518274#26518274
    public interface CatDeleteListener {
        void onDeleteCategory(String category);
    }

    private CatDeleteListener deleteListener;

    public void setDeleteListener(CatDeleteListener listener) {
        this.deleteListener = listener;
    }

    public CategoryPanel() {
        JLabel categoryLabel = new JLabel("Category");
        this.add(categoryLabel);

        categoryList = new JList<>();
        this.add(categoryList);

        // https://www.tutorialspoint.com/how-can-we-implement-right-click-menu-using-jpopupmenu-in-java
        popupMenu = new JPopupMenu();
        deleteCat = new JMenuItem("Delete");
        popupMenu.add(deleteCat);

        categoryList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int index = categoryList.locationToIndex(e.getPoint());
                    if (index != -1) {
                        categoryList.setSelectedIndex(index);
                        popupMenu.show(categoryList, e.getX(), e.getY());
                    }
                }
            }
        });

        deleteCat.addActionListener(e -> {
            String selected = categoryList.getSelectedValue();
            if (selected != null & deleteListener != null && !selected.equals("All")) {
                deleteListener.onDeleteCategory(selected);
            }
        });
    }

    public String getSelectedCategory() {
        return categoryList.getSelectedValue();
    }

    public void addSelectionListener(ListSelectionListener listener) {
        categoryList.addListSelectionListener(listener);
    }

    public void setCategories(String[] categories) {
        this.categoryList.setListData(categories);
    }
}
