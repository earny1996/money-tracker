package com.earny1996.moneytracker.persistencecontext.daos.hql;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IAccountDAO;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class AccountDAO extends AbstractDAO<Account> implements IAccountDAO {

    /**
     * READ
     * https://stackoverflow.com/questions/19637344/when-to-use-gateway-design-pattern
     * https://stackoverflow.com/questions/2810020/need-some-clarification-with-patterns-dao-x-gateway
     */

    private static AccountDAO me;

    /**
     * private Constructor for Singleton use
     */
    private AccountDAO(){
        super();
    }

    /**
     * Singletion
     * @return UserDAO
     */
    public static AccountDAO getInstance(){
        if(me == null){
            me = new AccountDAO();
        }
        return me;
    }

    /**
     * Returns a list of all accounts within the databse
     * @return List<Account>
     */
    @Override
    @Transactional
    public List<Account> getAll() {
        String query = "SELECT a from Account a";

        TypedQuery<Account> nativeQuery = entityManager.createQuery(query, Account.class);
        
        return nativeQuery.getResultList();
    }

    /**
     * Inserts a new Account into the database
     * @param account
     */
    @Override
    @Transactional
    public void persist(Account account) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.persist(account);
        et.commit();
    }

    /**
     * Deletes an account by the given Account Object
     * @param account
     */
    @Override
    @Transactional
    public void delete(Account account) {
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.remove(account);
        et.commit();
    }

    /**
     * Deletes an Account by its ID
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Long id) {

        // prepare SQL statement
        String query = "DELETE FROM accounts WHERE id = :id";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id",id);

        // get transaction
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            // start transaction
            transaction.begin();

            // execute sql query
            nativeQuery.executeUpdate();

            // commit transaction
            transaction.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
            transaction.rollback();
        }
        
    }

    @Override
    public Account getById(Long accountId) {
        return entityManager.find(Account.class, accountId);
    }

    @Override
    public List<Account> getByNameAndUser(String accountName, User user) {
        Session session = dataBase.getCurrentSession();
        if(!session.isOpen()){
            session = session.getSessionFactory().openSession();
        }
        // prepare SQL statement
        String query = "SELECT a FROM Account a WHERE a.name = :name AND a.user.id = :userid";

        // create sql query and add params
        
        TypedQuery<Account> typedQuery = session.createQuery(query, Account.class);
        typedQuery.setParameter("name", accountName);
        typedQuery.setParameter("userid", user.getUserId());

        return typedQuery.getResultList();
    }

    @Override
    public List<Account> getByUserId(Long userId) {
        // prepare SQL statement
        String query = "SELECT a FROM Account a WHERE a.user = :userid";

        // create sql query and add params
        TypedQuery<Account> typedQuery = entityManager.createQuery(query, Account.class);
        typedQuery.setParameter("userid", userId);

        return typedQuery.getResultList();
    }

    @Override
    public void update(Account t) {
        
        Session session = dataBase.getCurrentSession();
        if(!session.isOpen()){
            session = dataBase.getCurrentSession().getSessionFactory().openSession();
        }
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.merge(t);
        transaction.commit();
        //session.flush();
        session.close();
    }

    
}
