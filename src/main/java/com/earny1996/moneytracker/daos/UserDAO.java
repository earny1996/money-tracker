package com.earny1996.moneytracker.daos;

import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.database.DataBase;
import com.earny1996.moneytracker.daos.interfaces.IUserDAO;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.persistence.*;
import javax.transaction.Transactional;

public class UserDAO extends AbstractDAO<User> implements IUserDAO {

    @PersistenceContext
    private DataBase dataBase;
    private EntityManagerFactory factory;
    private EntityManager entityManager;
    private static UserDAO me;

    /**
     * private Constructor for Singleton use
     */
    private UserDAO(){
        dataBase = DataBase.getInstance();
        factory = dataBase.createEntityManagerFactoryByUnitName("money");
        entityManager = factory.createEntityManager();
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
        // prepare SQL statement
        String query = "SELECT id, firstName, lastName, email, password FROM users WHERE id = :id";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id", id);
        List<Object[]> resultList = nativeQuery.getResultList();
        List<User> userList = getUserByResultList(resultList);

        if(userList.size() > 1){
            throw new RuntimeException("More than 1 User for id '" + id + "' found.");
        } else if(userList.isEmpty()){
            return null;
        }

        return userList.get(0);
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
        String query = "SELECT id, firstname, lastname, email, password from users WHERE firstname LIKE :lastname;";

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("lastname", lastName);

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

        List<Object[]> resultList = nativeQuery.getResultList();
        List<User> userList = getUserByResultList(resultList);

        return userList;
    }

    /**
     * Updates the database fields of a user with the given id and
     *
     * @param updateParams
     * @param id
     */
    @Override
    @Transactional
    public void update(Map<String, String> updateParams, Long id) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE users SET ");

        AtomicInteger counter = new AtomicInteger();
        updateParams.forEach((key, value) -> {
            queryBuilder.append(key);
            queryBuilder.append(" = ");
            queryBuilder.append(value);

            counter.addAndGet(1);
            if(counter.get() < updateParams.size()){
                queryBuilder.append(", ");
            }
        });

        queryBuilder.append(" WHERE id = ");
        queryBuilder.append(id);
        queryBuilder.append(";");
    }

    /**
     * Builds a parameter Map of fields to update
     * for given user
     * @param user
     */
    @Override
    public void save(User user) {
        Map<String, String> updateParams = new HashMap<>();
        updateParams.put("firstname", user.getFirstName());
        updateParams.put("lastname", user.getLastName());
        updateParams.put("email", user.getEmail());
        updateParams.put("password", user.getPassword());

        // call update Method
        update(updateParams, user.getUserId());
    }

    /**
     * Deletes a user by the given User Object
     * @param user
     */
    @Override
    @Transactional
    public void delete(User user) {
        this.deleteById(user.getUserId());
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
            // close entityManager & factory
            //entityManager.close();
            //factory.close();
        }
    }

    /**
     * Inserts a new Users into the database
     * @param user
     */
    @Override
    @Transactional
    public void persist(User user) {

        // prepare SQL statement
        String query = "INSERT INTO users(id, firstname, lastname, email, password) VALUES(:id, :firstname, :lastname, :email, :password);";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id", user.getUserId());
        nativeQuery.setParameter("firstname", user.getFirstName());
        nativeQuery.setParameter("lastname", user.getLastName());
        nativeQuery.setParameter("email", user.getEmail());
        nativeQuery.setParameter("password", user.getPassword());

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
}