package com.earny1996.moneytracker.test;

import java.time.LocalDateTime;
import java.util.List;

import com.earny1996.moneytracker.controller.AccountController;
import com.earny1996.moneytracker.controller.TransactionController;
import com.earny1996.moneytracker.controller.UserController;
import com.earny1996.moneytracker.controller.interfaces.IAccountController;
import com.earny1996.moneytracker.controller.interfaces.ITransactionController;
import com.earny1996.moneytracker.controller.interfaces.IUserController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;

public class Main{


    public static void main(String[] args){
        IUserController userController = new UserController();
        IAccountController accountController = new AccountController();
        ITransactionController transactionController = new TransactionController();

        Menu menu = new Menu();
        menu.run();

        //createData();

        //User user = userController.getById(2020930160148801519L);
        //User newUser = createUser("Gustav", "Gans", "gustav-ganz@entenhausen.de", "duckduckgo");
        //Account accounte = createAccount("Schulden", newUser, 1400.0, "EUR");

        User user = userController.getById(2020109160226421096L);

        user.getAccounts().stream().forEach(account -> System.out.println(account.toString()));

        System.out.println("#####################################");
        Account kasse = accountController.getAccountsByNameAndUser("Kasse", user).get(0);
        System.out.println(kasse.getName() + " | " + kasse.getBalance() + " | " + kasse.getUser().getFirstName());
        System.out.println("#####################################");

        Account bank = accountController.getAccountsByNameAndUser("Bank", user).get(0);

        Transaction tr = transactionController.createTransaction("Geld abgehoben", null, user, bank, kasse, LocalDateTime.now(), 150.0);
        System.out.println("Transaction title " + tr.getTitle());
        
        user.getAccounts().stream().forEach(account -> System.out.println(account.toString()));

        List<Transaction> userTransactions = transactionController.getAllUserTransactions(user);

        userTransactions.stream().forEach(userTransaction -> System.out.println(userTransaction.toString()));

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

    private static Account createAccount(String name, User user, Double balance, String currencyCode){
        IAccountController accountController = new AccountController();
        Account account = new Account(name, balance, currencyCode, user);
        accountController.saveAccount(account);
        return account;
    }
}