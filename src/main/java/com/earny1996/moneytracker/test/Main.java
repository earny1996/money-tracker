package com.earny1996.moneytracker.test;

import java.util.List;

import com.earny1996.moneytracker.gateways.AccountGateway;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.AccountDAO;
import com.earny1996.moneytracker.persistencecontext.daos.UserDAO;

public class Main{

    public static void main(String[] args){
        UserDAO userDao = UserDAO.getInstance();
        User user = userDao.getByEmail("rene1996neumann@web.de");

        AccountDAO accountGateway = AccountGateway.getInstance();
        

        List<Account> accounts = accountGateway.getAll();
        for(Account account : accounts){
            System.out.println(account.toString());
        }

        Account kasse = accountGateway.getByNameAndUser("Kasse", user).get(0);

        accountGateway.add(kasse, 50);
        accountGateway.save(kasse);
    }
}