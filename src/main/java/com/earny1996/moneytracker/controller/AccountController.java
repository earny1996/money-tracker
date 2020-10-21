package com.earny1996.moneytracker.controller;

import java.util.List;

import com.earny1996.moneytracker.controller.interfaces.IAccountController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.hql.AccountDAO;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IAccountDAO;

public class AccountController implements IAccountController{

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
        IAccountDAO accountDAO = AccountDAO.getInstance();
        List<Account> accounts = accountDAO.getByUserId(user.getUserId());

        return accounts;
    }

    @Override
    public List<Account> getAccountsByNameAndUser(String accountName, User user){
        IAccountDAO accountDAO = AccountDAO.getInstance();
        List<Account> accounts = accountDAO.getByNameAndUser(accountName, user);

        return accounts;
    }

    @Override
    public List<Account> getAllAccounts(){
        IAccountDAO accountDAO = AccountDAO.getInstance();
        List<Account> accounts = accountDAO.getAll();

        return accounts;
    }

    @Override
    public void saveAccount(Account account){
        IAccountDAO accountDAO = AccountDAO.getInstance();
        accountDAO.save(account);
    }

    @Override
    public void deleteAccount(Account account){
        IAccountDAO accountDAO = AccountDAO.getInstance();
        accountDAO.delete(account);
    }
    
}
