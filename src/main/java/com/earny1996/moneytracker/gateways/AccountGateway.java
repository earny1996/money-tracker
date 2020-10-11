package com.earny1996.moneytracker.gateways;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.daos.AccountDAO;

public class AccountGateway extends AccountDAO{

    public void subtract(Account account, double amount){
        amount = account.getBalance() - amount;
        account.setBalance(amount);
    }

    public void add(Account account, double amount){
        amount += account.getBalance();
        account.setBalance(amount);
    }
    
}
