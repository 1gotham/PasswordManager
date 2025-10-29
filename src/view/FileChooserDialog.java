package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class FileChooserDialog {
    public Path chooseFile(Component parent, boolean saving) {
        JFileChooser chooser = new JFileChooser();
        if (saving) {
            chooser.setDialogTitle("Save file");
            chooser.setApproveButtonToolTipText("Save file");
        } else {
            chooser.setDialogTitle("Open file");
            chooser.setApproveButtonToolTipText("Open file");
        }
        chooser.setFileFilter(new FileNameExtensionFilter("Files (*.amg)", "amg"));
        chooser.setSelectedFile(new File("passwords.amg"));

        int result = saving ? chooser.showDialog(parent, "Save") : chooser.showDialog(parent, "Open");
        if (result == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".amg")) {
                f = new File(f.getParentFile(), f.getName() + ".amg");
            }
            return f.toPath();
        }
        return null;

    }
}
