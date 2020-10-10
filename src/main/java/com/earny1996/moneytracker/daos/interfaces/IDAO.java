package com.earny1996.moneytracker.daos.interfaces;

import java.util.List;
import java.util.Map;

public interface IDAO<T> {

    T getById(Long id);

    List<T> getAll();

    void update(String tablename, Map<String, String> updateFields, Long id);

    void delete(T t);

    void deleteById(Long id);

    void save(T t);

    void persist(T t);

}