package com.earny1996.moneytracker.controller;

import java.util.List;

import com.earny1996.moneytracker.controller.interfaces.IUserController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.hql.UserDAO;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IUserDAO;


public class UserController implements IUserController {

    private IUserDAO userDAO;

    public UserController(){
        userDAO = UserDAO.getInstance();
    }

    @Override
    public void createUser(User user){
        userDAO.persist(user);
    }

    @Override
    public void saveUser(User user){
        userDAO.persist(user);
    }

    @Override
    public void deleteUser(User user){
        userDAO.delete(user);
    }

    @Override
    public List<User> getByFirstname(String firstName){
        return userDAO.getByFirstName(firstName); 
    }

    @Override
    public List<User> getByLastname(String lastName){
        return userDAO.getByLastName(lastName);
    }

    @Override
    public User getByEmail(String email){
        return userDAO.getByEmail(email);
    }

    @Override
    public User getById(Long id){
        return userDAO.getById(id);
    }

    @Override
    public void changeFirstname(String firstName, User user) {
        user.setFirstName(firstName);
    }

    @Override
    public void changeLastname(String lastName, User user) {
        user.setLastName(lastName);
    }

    @Override
    public void changeEmail(String email, User user) {
       user.setEmail(email);
    }

    @Override
    public void changePassword(String password, User user) {
        user.setPassword(password);
    }

    @Override
    public void addAccount(User user, Account account) {
        user.addAccount(account);
    }

}
