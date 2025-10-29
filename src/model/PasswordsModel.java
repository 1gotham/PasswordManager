package model;

import java.util.ArrayList;
import java.util.List;

public class PasswordsModel {
    private List<EntryModel> passwords = new ArrayList<>();

    public void addPassword(EntryModel p) {
        passwords.add(p);
    }

    public List<EntryModel> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<EntryModel> passwords) {
        this.passwords = passwords;
    }

    public void removePassword(EntryModel entry) {
        passwords.remove(entry);
    }
}
