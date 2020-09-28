package com.earny1996.moneytracker.daos;

import com.earny1996.moneytracker.beans.User;
import com.earny1996.moneytracker.daos.database.DataBase;
import com.earny1996.moneytracker.daos.interfaces.IUserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.*;
import javax.persistence.*;
import javax.xml.crypto.Data;

@Entity
@Table(name = "user")
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
        return null;
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
    public void delete(User user) {

    }

    @Override
    public void save(User user) {

    }

    @Override
    public void persist() {

    }

    @Override
    public void persist(User user) {

    }
}