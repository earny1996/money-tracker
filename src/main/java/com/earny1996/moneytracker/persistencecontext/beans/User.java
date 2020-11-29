package com.earny1996.moneytracker.persistencecontext.beans;

import com.earny1996.moneytracker.security.Authenticator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractBean implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 2L;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "id")
    @Id
    private Long userId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy="user", cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<Account>();

    public User(){
        
    }

    public User(Long id, String firstName, String lastName, String email, String password) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUserId(id);
    }

    public User(String firstName, String lastName, String email, String password, boolean encryptPassword) {
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        if (encryptPassword) {
            Authenticator authenticator = Authenticator.getInstance();
            password = authenticator.generateHash(password);
        }
        this.setPassword(password);
        this.setUserId(generateId());
    }

    /* Getter */
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        if(!this.accounts.contains(account)){
            this.accounts.add(account);
        }
    }

    /* Setter */
    public void setFirstName(String firstName) {
        if (firstName != null && firstName.length() > 0) {
            this.firstName = firstName;
        } else {
            throw new RuntimeException("Username is not valid.");
        }
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() == 0) {
            throw new RuntimeException("Lastname is not valid.");
        }
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        if (email == null || email.length() == 0) {
            throw new RuntimeException("Email is not valid.");
        }
        this.email = email;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}