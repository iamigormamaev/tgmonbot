package models;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;

    public User() {
    }

    public User(org.telegram.telegrambots.api.objects.User inUser) {
        id = inUser.getId();
        firstName = inUser.getFirstName();
        lastName = inUser.getLastName();
        userName = inUser.getUserName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
