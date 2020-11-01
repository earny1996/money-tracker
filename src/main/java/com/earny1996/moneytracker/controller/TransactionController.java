package com.earny1996.moneytracker.controller;

import com.earny1996.moneytracker.controller.interfaces.IAccountController;
import com.earny1996.moneytracker.controller.interfaces.ITransactionController;
import com.earny1996.moneytracker.controller.interfaces.IUserController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.hql.AccountDAO;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IAccountDAO;

import java.time.LocalDateTime;

public class TransactionController implements ITransactionController {
    @Override
    public void createTransaction(User transactionUser, Account fromAccount, Account toAccount, LocalDateTime executedDate, Double amount) {
        // get controller
        IAccountController accountController = new AccountController();

        // create transaction
        Transaction transaction = new Transaction(transactionUser, fromAccount, toAccount, amount, executedDate);

        // process accounts
        accountController.subtract(fromAccount, amount);
        accountController.add(toAccount, amount);

        // save
        this.saveTransaction(transaction);
        accountController.updateAccount(fromAccount);
        accountController.updateAccount(toAccount);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        // TODO: implement save -> create transactionDAO
    }
}
