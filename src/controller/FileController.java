package controller;

import model.FileManager;
import model.PasswordEncryption;
import model.PasswordsModel;
import view.FileChooserDialog;
import view.MasterPassDialog;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class FileController {
    FileChooserDialog fileChooser;
    FileManager fileManager;
    PasswordsModel passwordsModel;
    PasswordEncryption passEnc;
    private Path lastPath;

    public FileController(PasswordsModel passwordsModel) {
        this.passwordsModel = passwordsModel;
        fileChooser = new FileChooserDialog();
        fileManager = new FileManager();
        passEnc = new PasswordEncryption(null);
    }

    public void saveFile(Component parent){
        Path chosen = fileChooser.chooseFile(parent, true);
        if(chosen == null) return;

        fileManager.setFilePath(chosen);
        try {
            fileManager.saveFile(passwordsModel, passEnc);
            lastPath = chosen;
            JOptionPane.showMessageDialog(parent,
                    "File saved:\n" + chosen,
                    "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex){
            JOptionPane.showMessageDialog(parent,
                    "Save error" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void openFile(Component parent){
        Path chosen = fileChooser.chooseFile(parent, false);
        if(chosen == null) return;

        fileManager.setFilePath(chosen);
        MasterPassDialog passDialog = new MasterPassDialog((JFrame) parent);
        passEnc.setMasterPassword(passDialog.getResult());
        if(passEnc.getMasterPassword() == null) return;
        try {
            fileManager.openFile(passwordsModel, passEnc);
            lastPath = chosen;
            String lastAttempt = fileManager.getLastAttempt();
            if(lastAttempt != null){
                JOptionPane.showMessageDialog(parent,
                    "Last file open attempt: " + Arrays.toString(lastAttempt.split("%")),
                    "Last Attempt", JOptionPane.INFORMATION_MESSAGE);
            }
            fileManager.updateLastAttempt(passEnc, passwordsModel);
            JOptionPane.showMessageDialog(parent,
                    "File opened:\n" + chosen,
                    "OK", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex){
            JOptionPane.showMessageDialog(parent,
                    "Open error" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void setMasterPassword(String msPass){
        passEnc.setMasterPassword(msPass);
    }

    public String getFileInfo(){
        //https://stackoverflow.com/questions/1090098/newline-in-jlabel
        return "<html>" + "Last open attempt : " + fileManager.getLastAttempt() + "<br>" + "File path: " + lastPath.toString()
                + "<br>" + "Entries count: " + passwordsModel.getPasswords().size() + "</html";
    }
}
