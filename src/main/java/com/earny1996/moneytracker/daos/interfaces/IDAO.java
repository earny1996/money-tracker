package com.earny1996.moneytracker.daos.interfaces;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {

    Optional<T> getById(Long id);

    List<T> getAll();

    void update(T t, String[] params);

    void delete(T t);

    void save(T t);

    void persist();

    void persist(T t);
}