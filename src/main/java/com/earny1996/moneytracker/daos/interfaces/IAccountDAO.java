package com.earny1996.moneytracker.daos.interfaces;

import java.util.List;

import com.earny1996.moneytracker.beans.Account;
import com.earny1996.moneytracker.beans.User;

public interface IAccountDAO extends IDAO<Account> {

    Account getById(Long accountId);

    List<Account> getByNameAndUser(String accountName, User user);
    
}
