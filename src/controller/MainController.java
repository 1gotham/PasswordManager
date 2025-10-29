package controller;

import model.EntryModel;
import model.PasswordEncryption;
import model.PasswordsModel;
import view.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class MainController {
    private final MainFrame view;
    private final EntryModel entryModel;
    private final PasswordsModel passwords;
    private final FileController fileController;
    private final InitDialog initDialog;

    public MainController(MainFrame view) {
        this.view = view;
        this.passwords = new PasswordsModel();
        entryModel = new EntryModel();
        fileController = new FileController(passwords);
        initDialog = new InitDialog(view);
    }

    public void init() {
        view.setVisible(true);
        view.getDataPanel().setPasswords(Collections.emptyList());
        initSearchBar();
        initDialog();
        initToolbar();
        initCategoryPanel();
        initEntryMenu();
    }

    private void onAddPassword() {
        AddPasswordDialog dialog = new AddPasswordDialog(view);
        dialog.setVisible(true);
        EntryModel result = dialog.getResult();
        if (result != null) {
            passwords.addPassword(result);
            view.getDataPanel().setPasswords(passwords.getPasswords());
            entryModel.incIdCounter();
            updateCategoryList();
            view.getDataInfoPanel().setInfoLabel(fileController.getFileInfo());
        }
    }

    private void initDialog() {
        initDialog.getBtnNewFile().addActionListener(e -> {
            SetMasterDialog msDialog = new SetMasterDialog(view);
            if (msDialog.getResult() != null) {
                fileController.setMasterPassword(msDialog.getResult());
            } else {
                System.exit(0);
            }
            initDialog.setVisible(false);
        });
        initDialog.getBtnOpenFile().addActionListener(e -> {
            initDialog.setVisible(false);
            fileController.openFile(view);
            view.getDataPanel().setPasswords(passwords.getPasswords());
            updateCategoryList();
            view.getDataInfoPanel().setInfoLabel(fileController.getFileInfo());

        });
        initDialog.setVisible(true);
    }

    private void initToolbar() {
        view.getToolBarView().getBtnAdd().addActionListener(e -> onAddPassword());
        view.getToolBarView().getBtnSave().addActionListener(e -> fileController.saveFile(view));
        view.getToolBarView().getBtnOpen().addActionListener(e -> {
            fileController.openFile(view);
            view.getDataPanel().setPasswords(passwords.getPasswords());
            updateCategoryList();
            view.getDataInfoPanel().setInfoLabel(fileController.getFileInfo());
        });
        view.getToolBarView().getComboBoxSort().addActionListener(e -> {
            String selected = (String) view.getToolBarView().getComboBoxSort().getSelectedItem();
            if (selected != null) {
                sortEntries(selected);
            }
        });
    }

    private void initCategoryPanel() {
        view.getCategoryPanel().addSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = view.getCategoryPanel().getSelectedCategory();
                System.out.println("Użytkownik wybrał: " + selected);
                if (selected != null) {
                    filterByCategory(selected);
                }
            }
        });

        view.getCategoryPanel().setDeleteListener(categoryToDelete -> {
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Do you want to delete category: " + categoryToDelete + " together with all entries?",
                    "Yes",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                List<EntryModel> rest = passwords.getPasswords().stream()
                        .filter(e -> !e.getCategory().equals(categoryToDelete))
                        .collect(Collectors.toList());
                passwords.setPasswords(rest);
                view.getDataPanel().setPasswords(rest);
                updateCategoryList();
            }
        });
    }

    private void filterByCategory(String category) {
        if (category.equals("All")) {
            view.getDataPanel().setPasswords(passwords.getPasswords());
            return;
        }

        List<EntryModel> filtered = passwords.getPasswords().stream()
                .filter(e -> e.getCategory().equals(category))
                .collect(Collectors.toList());
        view.getDataPanel().setPasswords(filtered);
    }

    private void initSearchBar() {
        DocumentListener temp = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterPasswords();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterPasswords();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void filterPasswords() {
                String text = view.getSearchBar().getSearchText().toLowerCase().trim();
                if (text.isEmpty()) {
                    view.getDataPanel().setPasswords(passwords.getPasswords());
                } else {
                    List<EntryModel> filtered = passwords.getPasswords().stream()
                            .filter(entry -> entry.getName().toLowerCase().contains(text))
                            .collect(Collectors.toList());
                    view.getDataPanel().setPasswords(filtered);
                }
            }
        };
        view.getSearchBar().searchListener(temp);
    }

    private void updateCategoryList() {
        Set<String> uniqueCategories = passwords.getPasswords().stream()
                .map(x -> x.getCategory())
                .collect(Collectors.toCollection(TreeSet::new));
        String[] categories = new String[uniqueCategories.size() + 1];
        categories[0] = "All";
        int i = 1;
        for (String x : uniqueCategories) {
            categories[i++] = x;
        }

        view.getCategoryPanel().setCategories(categories);
    }

    private void initEntryMenu() {
        view.getDataPanel().setActionListener(new DataPanel.EntryActionListener() {
            @Override
            public void onDeleteEntry(EntryModel entry) {
                passwords.removePassword(entry);
                view.getDataPanel().setPasswords(passwords.getPasswords());
                updateCategoryList();
                view.getDataInfoPanel().setInfoLabel(fileController.getFileInfo());
            }

            @Override
            public void onEditEntry(EntryModel entry) {
                AddPasswordDialog dialog = new AddPasswordDialog(view, entry);
                dialog.setTitle("Edit entry");
                dialog.setVisible(true);
                EntryModel result = dialog.getResult();
                if (result != null) {
                    entry.setName(result.getName());
                    entry.setLogin(result.getLogin());
                    entry.setPassword(result.getPassword());
                    entry.setCategory(result.getCategory());
                    view.getDataPanel().setPasswords(passwords.getPasswords());
                    updateCategoryList();
                    view.getDataInfoPanel().setInfoLabel(fileController.getFileInfo());
                }
                ;
            }
        });
    }

    private void sortEntries(String byWhat) {
        List<EntryModel> sorted = passwords.getPasswords().stream()
                .sorted((e1, e2) -> switch (byWhat) {
                    case "ID" -> e1.getId().compareTo(e2.getId());
                    case "NAME" -> e1.getName().compareTo(e2.getName());
                    case "LOGIN" -> e1.getLogin().compareTo(e2.getLogin());
                    default -> 0;
                })
                .collect(Collectors.toList());

        view.getDataPanel().setPasswords(sorted);
    }
}
