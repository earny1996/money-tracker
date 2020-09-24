package com.earny1996.moneytracker.dao;

import com.earny1996.moneytracker.security.Authenticator;
import com.earny1996.moneytracker.toname.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserDAO implements DAO<User> {

    private Authenticator authenticator = Authenticator.getInstance();

    private List<User> users = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String userId;
    private String password;
    private String email;

    private boolean isLoggedIn = false;

    /* Getter */
    @Column(name = "firstname")
    public String getFirstName(){
        return this.firstName;
    }

    @Column(name = "lastname")
    public String getLastName() {
        return lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "id")
    @Id
    public String getUserId() {
        return this.userId;
    }

    @Column(name = "password")
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
        this.password = authenticator.generateHash(password);
    }

    public void login(String password){
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
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void update(User user, String[] params) {
        user.setFirstName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        user.setLastName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        user.setEmail(Objects.requireNonNull(
                params[1], "Email cannot be null"));

        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void persist() {
        this.persist((User) this);
    }

    @Override
    public void persist(User user) {
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