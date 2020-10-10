package com.earny1996.moneytracker.daos;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.earny1996.moneytracker.beans.Account;
import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.database.DataBase;
import com.earny1996.moneytracker.daos.interfaces.IAccountDAO;

public class AccountDAO extends AbstractDAO<Account> implements IAccountDAO {

    private static AccountDAO me;

    /**
     * private Constructor for Singleton use
     */
    private AccountDAO(){
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
        List<Account> userList = getAccountByResultList(resultList);

        if(userList.size() > 1){
            throw new RuntimeException("More than 1 User for id '" + accountId + "' found.");
        } else if(userList.isEmpty()){
            return null;
        }

        return userList.get(0);
    }

    @Override
    public List<Account> getByNameAndUser(String accountName, User user) {
        // TODO Auto-generated method stub
        return null;
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
            accountList.add(account);
        }
        return accountList;
    }

    
}
