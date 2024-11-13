package org.js.programmingwindowapplications.animalshelterUI;

public class ShelterFacade {
    private final AccountsManager accountsManager;

    public ShelterFacade(AccountsManager accountsManager) {
        this.accountsManager = accountsManager;
    }

    public String validateUser(String username, String password) {
        Account account = accountsManager.findAccount(username, password);
        if (account instanceof AdminAccount) {
            return "admin";
        } else if (account instanceof ClientAccount) {
            return "user";
        }
        return null;
    }

    public AccountsManager getAccountsManager() {
        return accountsManager;
    }
}
