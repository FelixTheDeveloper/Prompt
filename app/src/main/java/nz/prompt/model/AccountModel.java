package nz.prompt.model;

public class AccountModel {
    private int AccountID;
    private String Email;
    private String Password;

    public AccountModel(String email, String password) {
        Email = email;
        Password = password;
    }

    public int getAccountID() {
        return AccountID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
