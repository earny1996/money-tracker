package com.earny1996.moneytracker.controller.interfaces;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionController {

    Transaction createTransaction(String title, String description, User transactionUser, Account fromAccount, Account toAccount, LocalDateTime executedDate, Double amount);

    List<Transaction> getAllUserTransactions(User user);
    
    void saveTransaction(Transaction tr);
}
