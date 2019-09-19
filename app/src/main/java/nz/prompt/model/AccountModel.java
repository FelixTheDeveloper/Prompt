package nz.prompt.model;

public class AccountModel {
    private int AccountID;
    private String Email;
    private String Password;
    private UserModel User;

    public AccountModel(int id, String email, String password, UserModel user) {
        AccountID = id;
        Email = email;
        Password = password;
        User = user;
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

    public UserModel getUser() {
        return User;
    }

    public void setUser(UserModel user) {
        User = user;
    }
}
