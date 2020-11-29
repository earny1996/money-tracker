package com.earny1996.moneytracker.persistencecontext.daos.interfaces;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;

import java.util.List;

public interface ITransactionDAO extends IDAO<Transaction>{

    List<Transaction> getByUser(User user);

    List<Transaction> getByAccount(Account account);

}
