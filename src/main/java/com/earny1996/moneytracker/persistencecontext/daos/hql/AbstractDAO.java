package com.earny1996.moneytracker.persistencecontext.daos.hql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.transaction.Transaction;

import com.earny1996.moneytracker.persistencecontext.daos.database.DataBase;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IDAO;

public abstract class AbstractDAO<T> implements IDAO<T> {

    private AbstractDAO<T> me;

	protected DataBase dataBase;
    protected EntityManager entityManager;

    public AbstractDAO(){
        dataBase = DataBase.getInstance();
        entityManager = dataBase.getEntityManager();
    }

}
