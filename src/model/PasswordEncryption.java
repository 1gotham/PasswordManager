package model;

public class PasswordEncryption {
    String masterPassword;

    private static final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!&$%";

    public PasswordEncryption(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    public String encrypt(String input) {
        int key = 0;
        for (char character : masterPassword.toCharArray()) {
            key += character;
        }

        StringBuilder output = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            if (!Character.isWhitespace(character)) {
                int index = alphabet.indexOf(character);
                int transformed = (index + key) % alphabet.length();
                output.append(alphabet.charAt(transformed));
            } else {
                output.append(' ');
            }
        }
        return output.toString();
    }

    public String encryptAtt(String input, String masPass) {
        int key = 0;
        for (char character : masPass.toCharArray()) {
            key += character;
        }

        StringBuilder output = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            if (!Character.isWhitespace(character)) {
                int index = alphabet.indexOf(character);
                int transformed = (index + key) % alphabet.length();
                output.append(alphabet.charAt(transformed));
            } else {
                output.append(' ');
            }
        }
        return output.toString();
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public String decrypt(String input) {
        int key = 0;
        for (char character : masterPassword.toCharArray()) {
            key += character;
        }

        StringBuilder output = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            if (!Character.isWhitespace(character)) {
                int index = alphabet.indexOf(character);
                int transformed = (index - key % alphabet.length() + alphabet.length()) % alphabet.length();
                output.append(alphabet.charAt(transformed));
            } else {
                output.append(' ');
            }
        }
        return output.toString();
    }

    public String decryptAtt(String input, String masPass) {
        int key = 0;
        for (char character : masPass.toCharArray()) {
            key += character;
        }

        StringBuilder output = new StringBuilder(input.length());
        for (char character : input.toCharArray()) {
            if (!Character.isWhitespace(character)) {
                int index = alphabet.indexOf(character);
                int transformed = (index - key % alphabet.length() + alphabet.length()) % alphabet.length();
                output.append(alphabet.charAt(transformed));
            } else {
                output.append(' ');
            }
        }
        return output.toString();
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }
}
