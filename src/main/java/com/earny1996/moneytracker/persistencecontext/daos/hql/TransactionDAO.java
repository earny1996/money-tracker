package com.earny1996.moneytracker.persistencecontext.daos.hql;

import com.earny1996.moneytracker.controller.AccountController;
import com.earny1996.moneytracker.persistencecontext.beans.Account;
import com.earny1996.moneytracker.persistencecontext.beans.Transaction;
import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.database.DataBase;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.ITransactionDAO;
import org.hibernate.Session;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        return null;
    }

    @Override
    public List<Transaction> getByAccount(Account account) {
        return null;
    }

    @Override
    public Transaction getById(Long id) {
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }

    @Override
    public void delete(Transaction transaction) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void persist(Transaction transaction) {
        Session currentSession = DataBase.getCurrentSession();
        if(!currentSession.isOpen()){
            currentSession = dataBase.getCurrentSession().getSessionFactory().openSession();
        }
        org.hibernate.Transaction tr = currentSession.getTransaction();
        currentSession.beginTransaction();
        currentSession.persist(transaction);
        tr.commit();
    }

    @Override
    public void update(Transaction transaction) {

    }

    /**
     * Helper Method to parse NativeQuery.ResultList to List<Account>
     * @param
     *      resultList
     * @return
     *      List<Account>
     */
    private List<Transaction> getAccountByResultList(List<Object[]> resultList){
        List<Transaction> transactionsList = new ArrayList<>();
        for(Object[] objects : resultList){
            BigInteger id = (BigInteger) objects[0];
            String title = (String) objects[1];
            String description = (String) objects[2];
            BigInteger userId = (BigInteger) objects[3];
            BigInteger accountFromId = (BigInteger) objects[4];
            BigInteger accountToId = (BigInteger) objects[5];
            Double amount = (Double) objects[6];
            LocalDateTime executedDate = (LocalDateTime) objects[7];
            LocalDateTime createdDate = (LocalDateTime) objects[8];

            User user = null;
            if(userId != null){
                UserDAO userDAO = UserDAO.getInstance();
                user = userDAO.getById(userId.longValue());
            } else {
                throw new RuntimeException("No User");
            }

            Account fromAccount;
            Account toAccount;
            if(accountFromId != null && accountToId != null){
                AccountDAO accountDAO = AccountDAO.getInstance();
                fromAccount = accountDAO.getById(accountFromId.longValue());
                toAccount = accountDAO.getById(accountToId.longValue());
            } else {
                throw new RuntimeException("Accounts are null");
            }

            Transaction transaction = new Transaction(id.longValue(), title, description, user, fromAccount, toAccount, amount.doubleValue(), createdDate, executedDate);

            transactionsList.add(transaction);
        }
        return transactionsList;
    }
}
