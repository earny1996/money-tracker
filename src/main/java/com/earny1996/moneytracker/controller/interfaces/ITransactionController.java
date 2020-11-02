package com.earny1996.moneytracker.controller.interfaces;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;

import java.time.LocalDateTime;

public interface ITransactionController {

    Transaction createTransaction(String title, String description, User transactionUser, Account fromAccount, Account toAccount, LocalDateTime executedDate, Double amount);

    void saveTransaction(Transaction tr);
}
