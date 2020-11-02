package com.earny1996.moneytracker.controller;

import com.earny1996.moneytracker.controller.interfaces.IAccountController;
import com.earny1996.moneytracker.controller.interfaces.ITransactionController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.hql.TransactionDAO;

import java.time.LocalDateTime;

public class TransactionController implements ITransactionController {
    @Override
    public Transaction createTransaction(String title, String description, User transactionUser, Account fromAccount, Account toAccount, LocalDateTime executedDate, Double amount) {
        // get controller
        IAccountController accountController = new AccountController();

        // create transaction
        Transaction tr = new Transaction(title, description, transactionUser, fromAccount, toAccount, amount, executedDate);

        // process accounts
        accountController.subtract(fromAccount, amount);
        accountController.add(toAccount, amount);

        // save
        this.saveTransaction(tr);
        accountController.updateAccount(fromAccount);
        accountController.updateAccount(toAccount);

        return tr;
    }

    @Override
    public void saveTransaction(Transaction tr) {
        TransactionDAO transactionDAO = TransactionDAO.getInstance();
        transactionDAO.persist(tr);
    }
}
