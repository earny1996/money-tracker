package com.earny1996.moneytracker.persistencecontext.daos.hql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import com.earny1996.moneytracker.persistencecontext.daos.database.DataBase;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IDAO;

public abstract class AbstractDAO<T> implements IDAO<T> {

    private AbstractDAO<T> me;

    @PersistenceContext
	protected DataBase dataBase;
    protected EntityManagerFactory factory;
    protected EntityManager entityManager;

    public AbstractDAO(){
        dataBase = DataBase.getInstance();
        factory = dataBase.createEntityManagerFactoryByUnitName("money");
        entityManager = factory.createEntityManager();
    }

}
