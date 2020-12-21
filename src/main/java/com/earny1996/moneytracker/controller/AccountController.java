package com.earny1996.moneytracker.controller;

import java.util.List;

import com.earny1996.moneytracker.controller.interfaces.IAccountController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.hql.AccountDAO;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IAccountDAO;

public class AccountController implements IAccountController{

    private IAccountDAO accountDAO;

    public AccountController(){
        this.accountDAO = AccountDAO.getInstance();
    }

    @Override
    public void subtract(Account account, double amount) {
        amount = account.getBalance() - amount;
        account.setBalance(amount);
    }

    @Override
    public void add(Account account, double amount){
        amount += account.getBalance();
        account.setBalance(amount);
    }

    @Override
    public List<Account> getUserAccounts(User user) {
        List<Account> accounts = accountDAO.getByUserId(user.getUserId());

        return accounts;
    }

    @Override
    public List<Account> getAccountsByNameAndUser(String accountName, User user){
        List<Account> accounts = accountDAO.getByNameAndUser(accountName, user);

        return accounts;
    }

    @Override
    public List<Account> getAllAccounts(){
        List<Account> accounts = accountDAO.getAll();

        return accounts;
    }

    @Override
    public void saveAccount(Account account){
        accountDAO.persist(account);
    }

    @Override
    public void deleteAccount(Account account){
        accountDAO.delete(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountDAO.update(account);
    }
    
}
