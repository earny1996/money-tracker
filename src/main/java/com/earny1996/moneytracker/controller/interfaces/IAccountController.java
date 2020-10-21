package com.earny1996.moneytracker.controller.interfaces;

import java.util.List;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;

public interface IAccountController {

    public void subtract(Account account, double amount);

    public void add(Account account, double amount);

    public List<Account> getUserAccounts(User user);

    public List<Account> getAccountsByNameAndUser(String accountName, User user);

    public List<Account> getAllAccounts();

    public void saveAccount(Account account);

    public void deleteAccount(Account account);
    
}
