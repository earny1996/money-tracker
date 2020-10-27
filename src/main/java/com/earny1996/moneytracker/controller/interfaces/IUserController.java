package com.earny1996.moneytracker.controller.interfaces;

import java.util.List;

import com.earny1996.moneytracker.persistencecontext.beans.User;

public interface IUserController {

    public void createUser(User user);

    public void saveUser(User user);

    public void deleteUser(User user);

    public List<User> getByFirstname(String firstName);

    public List<User> getByLastname(String lastName);

    public User getByEmail(String email);

    public User getById(Long id);

    public void changeFirstname(String firstName, User user);

    public void changeLastname(String lastName, User user);

    public void changeEmail(String email, User user);

    public void changePassword(String password, User user);
    
}
