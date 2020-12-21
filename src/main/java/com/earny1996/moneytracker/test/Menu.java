package com.earny1996.moneytracker.test;

import java.util.List;
import java.util.Scanner;

import com.earny1996.moneytracker.controller.AccountController;
import com.earny1996.moneytracker.controller.UserController;
import com.earny1996.moneytracker.controller.interfaces.IUserController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;

public class Menu {

    private Scanner userInputSystem;

    public Menu(){
        userInputSystem = new Scanner(System.in);
    }

    public void run() {
        doMainMenu();
    }

    private void doMainMenu() {
        String menuSystem = buildMainMenu();
        System.out.println(menuSystem);
        System.out.println("Your Choice: ");
        String userInput = userInputSystem.nextLine();

        int input = 0;
        try {
            input = Integer.parseInt(userInput);
        } catch (NumberFormatException nfe) {
            System.err.println(userInput + " is not a number. Try again");
            doMainMenu();
        }

        switch (input) {
            case 1:
                doCreateAccountMenu();
                break;
            case 2:
                doDeleteAccountMenu();
                break;
            case 3:
                doDisplayAllAccounts();
                break;
            case 4:
                doCheckBalanceOfAccountMenu();
                break;
            case 5:
                doViewAllTransactionsMenu();
                break;
            case 6:
                doCreateNewTransactionMenu();
                break;
            case 7: {
                userInputSystem.close();
                System.exit(1);
            }
        }
        doMainMenu();
    }

    private void doCreateAccountMenu() {
        IUserController uc = new UserController();
        AccountController ac = new AccountController();
        User user = uc.getById(2020109160226421096L);

        String accountName;
        Double startBalance = 0.0;
        String currencyCode;

        System.out.println("Create a new Account:");
        System.out.println("Type in a Name for the new account: ");
        accountName = userInputSystem.nextLine();
        System.out.println("Type in a Startbalance (leave blank if 0 is the startbalance):");
        String input = userInputSystem.nextLine();

        try {
            startBalance = Double.parseDouble(input);
        } catch (NumberFormatException nfe) {
            if (input.equals("")) {
                startBalance = 0.00;
            } else {
                System.err.println("The balance '" + input
                        + "' is not a valid value. Please start over again or head over to the main menu.");
                System.out.println("1: Start again            2: Main Menu");
                input = userInputSystem.nextLine();
                try {
                    int choice = Integer.parseInt(input);
                    if (choice == 1) {
                        doCreateAccountMenu();
                    } else if (choice == 2) {
                        doMainMenu();
                    }
                } catch (NumberFormatException error) {
                    System.err.println("Wrong Input. You will be redirected to the main menu.");
                    doMainMenu();
                }
            }
        }

        System.out.println("Currency Code should this account hold?");
        currencyCode = userInputSystem.nextLine();

        Account newAccount = new Account(accountName, startBalance, currencyCode, user);
        ac.saveAccount(newAccount);
        
        System.out.println("A new Account was created. Accountdetails:\n" + newAccount.toString());
        
    }

    private void doDeleteAccountMenu() {

    }

    private void doDisplayAllAccounts() {
        UserController uc = new UserController();
        AccountController ac = new AccountController();
        User user = uc.getById(2020109160226421096L);
        List<Account> accounts = ac.getUserAccounts(user);
        accounts.stream().forEach(account -> System.out.println(account.toString()));
    }

    private void doCheckBalanceOfAccountMenu(){

    }

    private void doViewAllTransactionsMenu(){

    }

    private void doCreateNewTransactionMenu(){

    }

    private String buildMainMenu(){
        StringBuilder builder = new StringBuilder();
        builder.append("What do you want to do?\n");
        builder.append("(1) Create an Account");
        builder.append("        ");
        builder.append("(2) Delete an Account");
        builder.append("\n");
        builder.append("(3) Display all Accounts");
        builder.append("        ");
        builder.append("(4) Check balance of an Account");
        builder.append("\n");
        builder.append("(5) View your Transactions");
        builder.append("        ");
        builder.append("(6) Create a new Transaction");
        builder.append("\n");
        builder.append("(7) Exit");  

        return builder.toString();
    }
    
}
