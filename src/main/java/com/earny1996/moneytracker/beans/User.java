package com.earny1996.moneytracker.beans;

import com.earny1996.moneytracker.security.Authenticator;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.UUID;

public class User {

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

    public User(String firstName, String lastName, String email, String password){
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUserId();
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
        if(lastName == null || lastName.length() == 0){
            throw new RuntimeException("Lastname is not valid.");
        }
        this.lastName = lastName;
    }


    public void setEmail(String email) {
        if(email == null || email.length() == 0){
            throw new RuntimeException("Email is not valid.");
        }
        this.email = email;
    }

    public void setUserId(){
        this.userId = generateUserId();
    }

    public void setPassword(String password){
        Authenticator authenticator = Authenticator.getInstance();
        this.password = authenticator.generateHash(password);
    }

    private String generateUserId(){
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        return id;
    }

    @Override
    public String toString() {
        /*
        return "User{" +
                "userName='" + this.getFirstName() + " " + this.getLastName() + '\'' +
                ", userId='" + this.getUserId() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                '}';

         */
        return "";
    }
}