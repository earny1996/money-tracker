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

        //createData();

        User user = userController.getById(2020930160148801519L);

        user.getAccounts().stream().forEach(account -> System.out.println(account.toString()));

        Account kasse = accountController.getAccountsByNameAndUser("Kasse", user).get(0);

        accountController.add(kasse, 50);
        accountController.updateAccount(kasse);
        System.out.println(user.getAccounts().size());
        
        user.getAccounts().stream().forEach(account -> System.out.println(account.toString()));

    }

    private static void createData(){
        IUserController userController = new UserController();
        createUser("Gustav", "Gans", "gustav-ganz@entenhausen.de", "duckduckgo");
        User user = userController.getById(2020930160148801519L);
        createAccount("Kasse", user, 0.0, "EUR");

        createAccount("Bank", user, 250.0, "EUR");
    }

    private static User createUser(String firstname, String lastname, String email, String password){
        IUserController userController = new UserController();
        User user = new User(firstname, lastname, email, password, true);
        userController.createUser(user);
        return user;
    }

    private static Account createAccount(String name, User user, Double startbalance, String currencyCode){
        IUserController userController = new UserController();
        IAccountController accountController = new AccountController();
        Account account = new Account(name, startbalance, currencyCode, user);
        userController.addAccount(user, account);
        accountController.createAccount(account);
        return account;
    }
}