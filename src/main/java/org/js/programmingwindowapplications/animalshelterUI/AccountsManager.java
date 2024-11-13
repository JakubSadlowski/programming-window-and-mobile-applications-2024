package org.js.programmingwindowapplications.animalshelterUI;

import java.util.ArrayList;
import java.util.List;

public class AccountsManager {
    private List<Account> accounts;

    public AccountsManager() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccount(String username, String password) {
        return accounts.stream()
                .filter(account -> account.getUsername().equals(username) && account.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
