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

        //User newUser = createUser("Gustav", "Gans", "gustav-ganz@entenhausen.de", "duckduckgo");
        //Account accounte = createAccount("Schulden", newUser, 1400.0, "EUR");

        User user = userController.getById(2020109160226421096L);
        user.getAccounts().stream().forEach(account -> System.out.println(account.toString()));

        System.out.println("#####################################");
        Account kasse = accountController.getAccountsByNameAndUser("Kasse", user).get(0);
        System.out.println(kasse.getName() + " | " + kasse.getBalance() + " | " + kasse.getUser().getFirstName());
        System.out.println("#####################################");

        accountController.add(kasse, 50);
        accountController.updateAccount(kasse);
        System.out.println(user.getAccounts().size());
        
        user.getAccounts().stream().forEach(account -> System.out.println(account.toString()));
    }

    private static User createUser(String firstname, String lastname, String email, String password){
        IUserController userController = new UserController();
        User user = new User(firstname, lastname, email, password, true);
        userController.createUser(user);
        return user;
    }

    private static Account createAccount(String name, User user, Double balance, String currencyCode){
        IAccountController accountController = new AccountController();
        Account account = new Account(name, balance, currencyCode, user);
        accountController.createAccount(account);
        return account;
    }
}