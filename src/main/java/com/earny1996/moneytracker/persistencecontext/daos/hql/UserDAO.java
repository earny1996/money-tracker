package com.earny1996.moneytracker.persistencecontext.daos.hql;

import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IUserDAO;

import org.hibernate.Session;

import java.math.BigInteger;
import java.util.*;
import javax.persistence.*;
import javax.transaction.Transactional;

public class UserDAO extends AbstractDAO<User> implements IUserDAO {

    private static UserDAO me;

    /**
     * private Constructor for Singleton use
     */
    private UserDAO(){
        super();
    }

    /**
     * Singletion
     * @return UserDAO
     */
    public static UserDAO getInstance(){
        if(me == null){
            me = new UserDAO();
        }
        return me;
    }

    /**
     * Get user by Id from database
     * @param id
     * @return User
     */
    @Override
    @Transactional
    public User getById(Long id) {
        Session session = dataBase.getCurrentSession();
        User user = session.find(User.class, id);
        //session.close();

        return user;
    }


    /**
     * Gets a List of users by their firstname.
     * @param
     *      firstName
     * @return
     *      List<User>
     */
    @Override
    @Transactional
    public List<User> getByFirstName(String firstName) {
        String query = "SELECT id, firstname, lastname, email, password from users WHERE firstname LIKE :firstname;";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("firstname", firstName);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
         
        List<User> userList = getUserByResultList(resultList);

        return userList;
    }

    /**
     * Helper Method to parse NativeQuery.ResultList to List<User>
     * @param
     *      resultList
     * @return
     *      List<User>
     */
    private List<User> getUserByResultList(List<Object[]> resultList){
        List<User> userList = new ArrayList<>();
        for(Object[] objects : resultList){
            BigInteger id = (BigInteger) objects[0];
            String firstName = (String) objects[1];
            String lastName = (String) objects[2];
            String userEmail = (String) objects[3];
            String password = (String) objects[4];

            User user = new User(id.longValue(), firstName, lastName, userEmail, password);
            userList.add(user);
        }
        return userList;
    }

    /**
     * Gets a List of users by their lastName.
     * @param
     *      lastName
     * @return
     *      List<User>
     */
    @Override
    @Transactional
    public List<User> getByLastName(String lastName) {
        String query = "SELECT id, firstname, lastname, email, password from users as u WHERE firstname LIKE :lastname INNER JOIN accounts ON u.id = accounts.fkusers";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("lastname", lastName);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
         
        List<User> userList = getUserByResultList(resultList);

        return userList;
    }

    /**
     * Returns a single User by its email.
     * Returns null if no user was found
     * @param email
     * @return User or Null
     */
    @Override
    @Transactional
    public User getByEmail(String email) {

        // prepare SQL statement
        String query = "SELECT id, firstName, lastName, email, password FROM users WHERE email = :email";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("email", email);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
         
        List<User> userList = getUserByResultList(resultList);

        if(userList.size() > 1){
            throw new RuntimeException("More than 1 User for email '" + email + "' found.");
        } else if(userList.isEmpty()){
            return null;
        }

        return userList.get(0);
    }

    /**
     * Returns a list of all users within the databse
     * @return List<User>
     */
    @Override
    @Transactional
    public List<User> getAll() {
        String query = "SELECT id, firstname, lastname, email, password from users;";

        Query nativeQuery = entityManager.createNativeQuery(query);

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = nativeQuery.getResultList();
         
        List<User> userList = getUserByResultList(resultList);

        return userList;
    }

    /**
     * Deletes a user by the given User Object
     * @param user
     */
    @Override
    @Transactional
    public void delete(User user) {
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
         
    }

    /**
     * Deletes a User by its ID
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Long id) {

        // prepare SQL statement
        String query = "DELETE FROM users WHERE id = :id";

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

    /**
     * Inserts a new Users into the database or updates an existing one
     * @param user
     */
    @Override
    @Transactional
    public void persist(User user) {
        // get transaction
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(User t) {
        entityManager.getTransaction().begin();
        entityManager.merge(t);
        entityManager.getTransaction().commit();
    }
}