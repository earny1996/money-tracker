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
    
}
