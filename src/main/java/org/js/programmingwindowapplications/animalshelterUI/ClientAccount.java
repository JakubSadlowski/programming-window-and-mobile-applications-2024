package org.js.programmingwindowapplications.animalshelterUI;

public class ClientAccount extends Account {
    private String firstName;
    private String lastName;

    public ClientAccount(String firstName, String lastName, String username,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
