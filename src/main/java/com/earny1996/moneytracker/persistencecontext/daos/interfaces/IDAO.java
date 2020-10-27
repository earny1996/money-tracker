package com.earny1996.moneytracker.persistencecontext.daos.interfaces;

import java.util.List;

public interface IDAO<T> {

    T getById(Long id);

    List<T> getAll();

    void delete(T t);

    void deleteById(Long id);

    void persist(T t);

    void update(T t);

}