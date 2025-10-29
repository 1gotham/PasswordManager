package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainFrame extends JFrame {
    private JSplitPane mainSplitPane;
    private CategoryPanel categoryPanel;
    private DataPanel dataPanel;
    private DataInfoPanel dataInfoPanel;
    private ToolBarView toolBarView;
    private FileChooserDialog fileChooserDialog;
    private SearchBar searchBar;

    public MainFrame() {
        super("Password Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640, 480);
        this.setMinimumSize(this.getSize());
        addComponents();

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> mainSplitPane.setDividerLocation(0.8));
            }
        });
    }

    public DataInfoPanel getDataInfoPanel() {
        return dataInfoPanel;
    }

    private void addComponents() {

        this.toolBarView = new ToolBarView();
        this.add(toolBarView, BorderLayout.NORTH);
        this.searchBar = new SearchBar();
        toolBarView.add(searchBar);
        this.categoryPanel = new CategoryPanel();
        this.dataPanel = new DataPanel();
        this.dataInfoPanel = new DataInfoPanel();
        JSplitPane dataSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoryPanel, dataPanel);
        mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, dataSplitPane, dataInfoPanel);
        mainSplitPane.setResizeWeight(1.0);
        this.add(mainSplitPane);

    }

    public ToolBarView getToolBarView() {
        return toolBarView;
    }

    public DataPanel getDataPanel() {
        return dataPanel;
    }

    public CategoryPanel getCategoryPanel() {
        return categoryPanel;
    }

    public SearchBar getSearchBar() {
        return searchBar;
    }
}
