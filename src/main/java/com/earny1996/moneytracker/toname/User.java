package com.earny1996.moneytracker.toname;

import com.earny1996.moneytracker.dao.UserDAO;

import java.util.List;

public class User extends UserDAO {

    private List<Account> accounts;

    public User(String firstName, String lastName, String email, String password){

        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUserId();
        this.persist(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + this.getFirstName() + " " + this.getLastName() + '\'' +
                ", userId='" + this.getUserId() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                '}';
    }
}