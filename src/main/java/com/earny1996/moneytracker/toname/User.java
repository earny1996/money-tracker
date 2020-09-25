package com.earny1996.moneytracker.toname;

import com.earny1996.moneytracker.dao.UserDAO;

import java.util.List;

public class User {

    private List<Account> accounts;
    private UserDAO daoLayer;

    public User(String firstName, String lastName, String email, String password){
        UserDAO userDao = new UserDAO(firstName, lastName, email, password);
        daoLayer = userDao;
    }

    public UserDAO getDaoLayer(){
        return this.daoLayer;
    }

    @Override
    public String toString() {
        /*
        return "User{" +
                "userName='" + this.getFirstName() + " " + this.getLastName() + '\'' +
                ", userId='" + this.getUserId() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                '}';

         */
        return "";
    }
}