package com.earny1996.moneytracker.persistencecontext.daos.interfaces;

import java.util.List;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;

public interface IAccountDAO extends IDAO<Account> {

    Account getById(Long accountId);

    List<Account> getByNameAndUser(String accountName, User user);
    
}
