package com.earny1996.moneytracker.persistencecontext.daos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.database.DataBase;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IAccountDAO;

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
    protected AccountDAO(){
        dataBase = DataBase.getInstance();
        factory = dataBase.createEntityManagerFactoryByUnitName("money");
        entityManager = factory.createEntityManager();
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

        // prepare SQL statement
        String query = "INSERT INTO accounts(id, name, balance, currencycode, fkusers) VALUES(:id, :name, :balance, :currencycode, :fkusers);";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id", account.getId());
        nativeQuery.setParameter("name", account.getName());
        nativeQuery.setParameter("balance", account.getBalance());
        nativeQuery.setParameter("currencycode", account.getCurrencyCode());
        nativeQuery.setParameter("fkusers", account.getUser().getUserId());

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
            // close entityManager & factory
            //entityManager.close();
            //factory.close();
        }
    }

    /**
     * Builds a parameter Map of fields to update
     * for given account
     * @param account
     */
    @Override
    public void save(Account account) {
        Map<String, String> updateParams = new HashMap<>();
        updateParams.put("name", account.getName());
        updateParams.put("balance", String.valueOf(account.getBalance()));
        updateParams.put("currencycode", account.getCurrencyCode());
        updateParams.put("fkuser", account.getUser().getUserId().toString());

        // call update Method
        update("accounts", updateParams, account.getId());
    }

    /**
     * Deletes an account by the given Account Object
     * @param account
     */
    @Override
    @Transactional
    public void delete(Account account) {
        this.deleteById(account.getId());
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
            // close entityManager & factory
            //entityManager.close();
            //factory.close();
        }
    }

    @Override
    public Account getById(Long accountId) {
        // prepare SQL statement
        String query = "SELECT id, name, balance, currencycode, fkuser FROM accounts WHERE id = :id";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id", accountId);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
        List<Account> accountList = getAccountByResultList(resultList);

        if(accountList.size() > 1){
            throw new RuntimeException("More than 1 Account for id '" + accountId + "' found.");
        } else if(accountList.isEmpty()){
            return null;
        }

        return accountList.get(0);
    }

    @Override
    public List<Account> getByNameAndUser(String accountName, User user) {
        // prepare SQL statement
        String query = "SELECT id, name, balance, currencycode, fkusers FROM accounts WHERE name = :name AND fkusers = :userid";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("name", accountName);
        nativeQuery.setParameter("userid", user.getUserId());

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
        List<Account> accountList = getAccountByResultList(resultList);

        return accountList;
    }

    public List<Account> getByUser(User user) {
        // prepare SQL statement
        String query = "SELECT id, name, balance, currencycode, fkusers FROM accounts WHERE fkusers = :userid";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("userid", user.getUserId());

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

    
}
