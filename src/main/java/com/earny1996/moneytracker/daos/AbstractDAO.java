package com.earny1996.moneytracker.daos;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import com.earny1996.moneytracker.daos.database.DataBase;
import com.earny1996.moneytracker.daos.interfaces.IDAO;


import javax.transaction.Transactional;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractDAO<T> implements IDAO<T> {

    private AbstractDAO<T> me;

    @PersistenceContext
	protected DataBase dataBase;
    protected EntityManagerFactory factory;
    protected EntityManager entityManager;

    /**
     * Updates the database fields of a given table by the given id and params
     *
     * @param updateParams
     * @param id
     */
    @Override
    @Transactional
    public void update(String tablename, Map<String, String> updateParams, Long id) {
        if(tablename == null){
            throw new RuntimeException("No given table found to perform update.");
        }
        
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE" + tablename + " SET ");

        AtomicInteger counter = new AtomicInteger();
        updateParams.forEach((key, value) -> {
            queryBuilder.append(key);
            queryBuilder.append(" = ");
            queryBuilder.append(value);

            counter.addAndGet(1);
            if(counter.get() < updateParams.size()){
                queryBuilder.append(", ");
            }
        });

        queryBuilder.append(" WHERE id = ");
        queryBuilder.append(id);
        queryBuilder.append(";");
    }

}
