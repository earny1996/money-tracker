package com.earny1996.moneytracker.persistencecontext.daos.hql;

import com.earny1996.moneytracker.persistencecontext.beans.User;
import com.earny1996.moneytracker.persistencecontext.daos.interfaces.IUserDAO;
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
        return entityManager.find(User.class, id);
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
        String query = "SELECT u from User u WHERE u.firstname LIKE :firstname";

        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("firstname", firstName);

        return typedQuery.getResultList();
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
        String query = "SELECT u from User u WHERE u.lastname LIKE :lastname";

        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("lastname", lastName);

        return typedQuery.getResultList();
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
        String query = "SELECT u FROM User u WHERE u.email = :email";

        // create sql query and add params
        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("email", email);

        return typedQuery.getSingleResult();
    }

    /**
     * Returns a list of all users within the databse
     * @return List<User>
     */
    @Override
    @Transactional
    public List<User> getAll() {
        String query = "SELECT u from User u";

        TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);

        return typedQuery.getResultList();
    }

    /**
     * Deletes a user by the given User Object
     * @param user
     */
    @Override
    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
         
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
        EntityTransaction et = entityManager.getTransaction();
        et.begin();
        entityManager.persist(user);
        et.commit();
         
    }

    @Override
    public void update(User t) {
        entityManager.merge(t);
    }
}