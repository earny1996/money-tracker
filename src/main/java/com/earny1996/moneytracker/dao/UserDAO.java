package com.earny1996.moneytracker.dao;

import com.earny1996.moneytracker.security.Authenticator;
import com.earny1996.moneytracker.toname.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDAO implements DAO<UserDAO> {

    //private Authenticator authenticator = Authenticator.getInstance();

    //private List<User> users = new ArrayList<>();


    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "id")
    @Id
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    private boolean isLoggedIn = false;

    public UserDAO(String firstName, String lastName, String email, String password) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUserId();
        this.persist();
    }

    /* Getter */
    public String getFirstName(){
        return this.firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public String getEmail() {
        return email;
    }


    public String getUserId() {
        return this.userId;
    }


    public String getPassword() {
        return password;
    }

    /* Setter */
    public void setFirstName(String firstName){
        if(firstName != null && firstName.length() > 0){
            this.firstName = firstName;
        } else {
            throw new RuntimeException("Username is not valid.");
        }
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIsLoggedIn(boolean loginState){
        this.isLoggedIn = loginState;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(){
        this.userId = generateUserId();
    }

    public void setPassword(String password){
        Authenticator authenticator = Authenticator.getInstance();
        this.password = authenticator.generateHash(password);
    }

    public void login(String password){
        Authenticator authenticator = Authenticator.getInstance();
        boolean isValid = authenticator.authenticate(password, this.password);
        this.setIsLoggedIn(isValid);
        if(!isValid){
            throw new RuntimeException("Login not successfull");
        }
    }

    public void logout(){
        this.setIsLoggedIn(false);
    }

    private String generateUserId(){
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        return id;
    }

    @Override
    public Optional<UserDAO> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<UserDAO> getAll() {
        return null;
    }

    @Override
    public void update(UserDAO userDAO, String[] params) {

    }

    @Override
    public void delete(UserDAO userDAO) {

    }

    @Override
    public void save(UserDAO userDAO) {

    }

    @Override
    public void persist() {
        this.persist(this);
    }

    public void persist(UserDAO user) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("money");

        EntityManager entitiyManager = emfactory.createEntityManager();

        // begin transaction
        entitiyManager.getTransaction( ).begin( );

        entitiyManager.persist(user);
        entitiyManager.getTransaction( ).commit( );

        entitiyManager.close( );
        emfactory.close( );
    }

    public boolean getIsLoggedIn(){
        return this.isLoggedIn;
    }
}