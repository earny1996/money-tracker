package com.earny1996.moneytracker.test;

import java.util.List;

import com.earny1996.moneytracker.beans.Account;
import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.AccountDAO;
import com.earny1996.moneytracker.daos.UserDAO;

public class Main{

    public static void main(String[] args){
        UserDAO userDao = UserDAO.getInstance();
        User user = userDao.getByEmail("rene1996neumann@web.de");

        AccountDAO accountDao = AccountDAO.getInstance();
        
        Account account = new Account("bank", 1009.85, "EUR", user);

        accountDao.persist(account);

        user.toString();

        account.toString();
    }
}