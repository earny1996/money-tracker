package com.earny1996.moneytracker.daos.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IDAO<T> {

    T getById(Long id);

    List<T> getAll();

    void update(Map<String, String> updateFields, Long id);

    void delete(T t);

    void save(T t);

    void persist(T t);

}