package com.earny1996.moneytracker.test;

import java.util.List;

import com.earny1996.moneytracker.controller.AccountController;
import com.earny1996.moneytracker.controller.UserController;
import com.earny1996.moneytracker.controller.interfaces.IAccountController;
import com.earny1996.moneytracker.controller.interfaces.IUserController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;

public class Main{

    public static void main(String[] args){
        IUserController userController = new UserController();
        IAccountController accountController = new AccountController();

        User user = userController.getByEmail("rene1996neumann@web.de");

        List<Account> accounts = accountController.getUserAccounts(user);
        for(Account account : accounts){
            System.out.println(account.toString());
        }

        Account kasse = accountController.getAccountsByNameAndUser("Kasse", user).get(0);

        accountController.add(kasse, 50);
        accountController.saveAccount(kasse);
    }
}