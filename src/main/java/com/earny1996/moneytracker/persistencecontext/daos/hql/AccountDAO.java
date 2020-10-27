package com.earny1996.moneytracker.persistencecontext.daos.hql;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.TransactionManager;
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
     * protected Constructor for Singleton use
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
        String query = "SELECT id, name, balance, currencycode, fkusers from accounts;";

        Query nativeQuery = entityManager.createNativeQuery(query);
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
        
        List<Account> accountList = getAccountByResultList(resultList);

        return accountList;
    }

    /**
     * Inserts a new Account into the database
     * @param account
     */
    @Override
    @Transactional
    public void persist(Account account) {
        entityManager.persist(account);
        dataBase.getCurrentSession().flush();
    }

    /**
     * Deletes an account by the given Account Object
     * @param account
     */
    @Override
    @Transactional
    public void delete(Account account) {
        entityManager.remove(account);
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
        } finally {
             
        }
    }

    @Override
    public Account getById(Long accountId) {
        Account account = entityManager.find(Account.class, accountId);
         
        return account;
    }

    @Override
    public List<Account> getByNameAndUser(String accountName, User user) {
        Session session = dataBase.getCurrentSession();
        if(!session.isOpen()){
            session = session.getSessionFactory().openSession();
        }
        // prepare SQL statement
        String query = "SELECT id, name, balance, currencycode, fkusers FROM accounts WHERE name = :name AND fkusers = :userid";

        // create sql query and add params
        
        Query nativeQuery = session.createNativeQuery(query);
        nativeQuery.setParameter("name", accountName);
        nativeQuery.setParameter("userid", user.getUserId());

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
        session.close();
        List<Account> accountList = getAccountByResultList(resultList);

        return accountList;
    }

    @Override
    public List<Account> getByUserId(Long userId) {
        // prepare SQL statement
        String query = "SELECT id, name, balance, currencycode, fkusers FROM accounts WHERE fkusers = :userid";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userid", userId);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
         
        List<Account> accountList = getAccountByResultList(resultList);

        return accountList;
    }

    /**
     * Helper Method to parse NativeQuery.ResultList to List<Account>
     * @param
     *      resultList
     * @return
     *      List<Account>
     */
    private List<Account> getAccountByResultList(List<Object[]> resultList){
        List<Account> accountList = new ArrayList<>();
        for(Object[] objects : resultList){
            BigInteger id = (BigInteger) objects[0];
            String name = (String) objects[1];
            double balance = (double) objects[2];
            String currencyCode = (String) objects[3];
            BigInteger userId = (BigInteger) objects[4];
            
            User user = null;
            if(userId != null){
                UserDAO userDAO = UserDAO.getInstance();
                user = userDAO.getById(userId.longValue());
            }
            
            Account account = new Account(id.longValue(), name, balance, currencyCode, user);
            if(user != null){
                user.addAccount(account);
            }
            accountList.add(account);
        }
        return accountList;
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
