package com.earny1996.moneytracker.persistencecontext.daos.hql;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.ITransactionDAO;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class TransactionDAO extends AbstractDAO<Transaction> implements ITransactionDAO {

    private static TransactionDAO instance;

    public static TransactionDAO getInstance(){
        if(instance == null){
            instance = new TransactionDAO();
        }

        return instance;
    }

    @Override
    public List<Transaction> getByUser(User user) {
        String queryString = "SELECT t from Transaction t WHERE t.transactionUser.id = :userId";
        TypedQuery<Transaction> typedQuery = entityManager.createQuery(queryString, Transaction.class);
        typedQuery.setParameter("userId", user.getUserId());

        return typedQuery.getResultList();
    }

    @Override
    public List<Transaction> getByAccount(Account account) {
        String queryString = "SELECT t from Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId";
        TypedQuery<Transaction> typedQuery = entityManager.createQuery(queryString, Transaction.class);
        typedQuery.setParameter("accountId", account.getId());

        return typedQuery.getResultList();
    }

    @Override
    public Transaction getById(Long id) {
        String queryString = "SELECT t from Transaction t WHERE id = :id";
        TypedQuery<Transaction> typedQuery = entityManager.createQuery(queryString, Transaction.class);
        typedQuery.setParameter("id", id);

        return typedQuery.getSingleResult();
    }

    @Override
    public List<Transaction> getAll() {

        String queryString = "SELECT t from Transaction t";
        TypedQuery<Transaction> typedQuery = entityManager.createQuery(queryString, Transaction.class);

        return typedQuery.getResultList();
    }

    @Override
    public void delete(Transaction transaction) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.remove(transaction);
        et.commit();
    }

    @Override
    public void deleteById(Long id) {
        EntityTransaction et = entityManager.getTransaction();

        String queryString = "DELETE FROM transactions WHERE id = :id";
        Query query = entityManager.createNativeQuery(queryString);
        query.setParameter("id", id);

        et.begin();
        try{
            query.executeUpdate();
            et.commit();
        } catch(Exception e){
            et.rollback();
        }

    }

    @Override
    public void persist(Transaction transaction) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.persist(transaction);
        et.commit();
    }

    @Override
    public void update(Transaction transaction) {

    }

    
}
