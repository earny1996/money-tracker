package com.earny1996.moneytracker.daos;

import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.database.DataBase;
import com.earny1996.moneytracker.daos.interfaces.IUserDAO;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;
import javax.persistence.*;
import javax.transaction.Transactional;

public class UserDAO extends AbstractDAO<User> implements IUserDAO {


    @Override
    public List<User> getByFirstName(String firstName) {
        DataBase database = DataBase.getInstance();
        EntityManagerFactory factory = database.createEntityManagerFactoryByUnitName("money");
        EntityManager entityManager = factory.createEntityManager();

        String query = "select id, firstname, lastname, email, password from user;";

        Query nativeQuery = entityManager.createNativeQuery(query);
        List<Object[]> resultList = nativeQuery.getResultList();

        for(Object[] result : resultList){
            System.out.println(result[0] + " " + result[1]);
        }

        entityManager.close();
        factory.close();
        return null;
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        DataBase database = DataBase.getInstance();
        EntityManagerFactory factory = database.createEntityManagerFactoryByUnitName("money");
        EntityManager entityManager = factory.createEntityManager();

        // prepare SQL statement
        String query = "SELECT id, firstName, lastName, email, password FROM users WHERE email = :email";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("email", email);
        User user = null;
        try {

            // execute sql query
            List<Object[]> resultList = nativeQuery.getResultList();
            for(Object[] objects : resultList){
                BigInteger id = (BigInteger) objects[0];
                String firstName = (String) objects[1];
                String lastName = (String) objects[2];
                String userEmail = (String) objects[3];
                String password = (String) objects[4];

                user = new User(id.longValue(), firstName, lastName, userEmail, password);
            }


        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            // close entityManager & factory
            entityManager.close();
            factory.close();
        }
        return user;
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    @Transactional
    public void delete(User user) {
        DataBase database = DataBase.getInstance();
        EntityManagerFactory factory = database.createEntityManagerFactoryByUnitName("money");
        EntityManager entityManager = factory.createEntityManager();

        // prepare SQL statement
        String query = "DELETE FROM users WHERE id = :id";

        // create sql query and add params
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("id", user.getUserId());

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
            entityManager.close();
            factory.close();
        }
    }

    @Override
    public void save(User user) {

    }

    @Override
    @Transactional
    public void persist(User user) {
        DataBase database = DataBase.getInstance();
        EntityManagerFactory factory = database.createEntityManagerFactoryByUnitName("money");
        EntityManager entityManager = factory.createEntityManager();

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
            entityManager.close();
            factory.close();
        }
    }
}