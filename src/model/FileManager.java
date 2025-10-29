package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileManager {
    private Path filePath;
    private String lastAttempt;

    private static final String attemptPrefix = "(#@*!Y(#@!H#*(!@Y#(*G@!(*S!";
    private static final String attemptSufix = "#*(@!&#*@!&#*(!@&#*(!@#*&!#!@(";

    public String getLastAttempt() {
        return lastAttempt;
    }

    public void setLastAttempt(String lastAttempt) {
        this.lastAttempt = lastAttempt;
    }

    public void updateLastAttempt(PasswordEncryption passEnc, PasswordsModel passwordsModel) throws IOException {
        this.lastAttempt = LocalDateTime.now().toString();
        saveFile(passwordsModel, passEnc);
    }

    public FileManager() {
        this.lastAttempt = attemptPrefix + attemptSufix;
    };

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public void saveFile(PasswordsModel passwordsModel, PasswordEncryption passEnc) throws IOException {
        if (filePath == null) {
            throw new IllegalStateException("File path is not set!");
        }
        List<EntryModel> passwords = passwordsModel.getPasswords();
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (EntryModel e : passwords) {
                String toWrite = e.getId() + " " + e.getName() + " " + e.getLogin() + " " + e.getPassword() + " "
                        + e.getCategory();
                toWrite = passEnc.encrypt(toWrite);
                writer.write(toWrite);
                writer.newLine();
            }
            if (lastAttempt != null) {
                String attemptToWrite = attemptPrefix + passEnc.encryptAtt(lastAttempt, "att") + attemptSufix;
                writer.write(attemptToWrite);
                writer.newLine();
            }
        }
    }

    public void openFile(PasswordsModel passwordsModel, PasswordEncryption passEnc) throws IOException {
        if (filePath == null) {
            throw new IllegalStateException("File path is not set!");
        }
        List<EntryModel> passwords = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(attemptPrefix) && line.endsWith(attemptSufix)) {
                    String attempt = line.substring(attemptPrefix.length(), line.length() - attemptSufix.length());
                    lastAttempt = passEnc.decryptAtt(attempt, "att");
                } else {
                    line = passEnc.decrypt(line);
                    String[] parts = line.split(" ", 5);
                    EntryModel temp = new EntryModel();
                    temp.setId(parts[0].trim());
                    temp.setName(parts[1].trim());
                    temp.setLogin(parts[2].trim());
                    temp.setPassword(parts[3].trim());
                    temp.setCategory(parts[4].trim());
                    passwords.add(temp);
                }
            }
        }
        passwordsModel.setPasswords(passwords);
    }
}
