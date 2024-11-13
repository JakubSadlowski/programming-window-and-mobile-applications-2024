package org.js.programmingwindowapplications.animalshelterUI;

import java.util.ArrayList;
import java.util.List;

public class AccountsManager {
    private List<Account> accounts;

    public AccountsManager() {
        this.accounts = new ArrayList<>();
        initializeDefaultAccounts();
    }

    private void initializeDefaultAccounts() {
        accounts.add(new AdminAccount());
        accounts.add(new ClientAccount("John", "Doe", "johndoe", "password123"));
        accounts.add(new ClientAccount("Jane", "Smith", "janesmith", "mypassword"));
    }

    public Account findAccount(String username, String password) {
        return accounts.stream()
                .filter(account -> account.getUsername().equals(username) && account.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
