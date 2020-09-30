package com.earny1996.moneytracker.daos.interfaces;

import com.earny1996.moneytracker.beans.User;

import java.util.List;

public interface IUserDAO extends IDAO<User>{

    List<User> getByFirstName(String firstName);

    List<User> getByLastName(String lastName);

    User getByEmail(String email);

    void deleteById(Long id);
}
