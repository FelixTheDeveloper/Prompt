package nz.prompt.model;

public class UserModel {
    private int ID;
    private String FirstName;
    private String LastName;
    private int Age;
    private int PhoneNumber;
    private int Budget;

    public UserModel(int id, String firstName, String lastName, int age, int phoneNumber, int budget) {
        ID = id;
        FirstName = firstName;
        LastName = lastName;
        Age = age;
        PhoneNumber = phoneNumber;
        Budget = budget;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getAge() {
        return Age;
    }

    public boolean setAge(int age) {
        if (age > 0)
        {
            Age = age;
            return true;
        }

        return false;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getBudget() {
        return Budget;
    }

    public boolean setBudget(int budget) {
        if (budget >= 0) {
            Budget = budget;
            return true;
        }

        return false;
    }
}
