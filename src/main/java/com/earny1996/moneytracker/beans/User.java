package com.earny1996.moneytracker.beans;

import com.earny1996.moneytracker.security.Authenticator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

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

    public User(Long id, String firstName, String lastName, String email, String password){
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setUserId(id);
    }

    public User(String firstName, String lastName, String email, String password, boolean encryptPassword){
        this.setEmail(email);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        if(encryptPassword){
            Authenticator authenticator = Authenticator.getInstance();
            password = authenticator.generateHash(password);
        }
        this.setPassword(password);
        Long id = generateUserId();
        this.setUserId(id);
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


    public Long getUserId() {
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

    public void setUserId(Long id){
        this.userId = id;
    }

    public void setPassword(String password){
        this.password = password;
    }

    private Long generateUserId(){
        Long systemMillis = System.currentTimeMillis();
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append(month);
        stringBuilder.append(day);
        stringBuilder.append(systemMillis);

        String idValue = stringBuilder.toString();
        if(idValue.length() > 19){
            idValue = idValue.substring(0,19);
        }

        return Long.valueOf(idValue);
    }

    @Override
    public String toString() {
        StringBuilder toStringBuilder = new StringBuilder();
        toStringBuilder.append("##################################################");
        toStringBuilder.append("\n");
        toStringBuilder.append("Vorname: ");
        toStringBuilder.append(this.getFirstName());
        toStringBuilder.append("\n");
        toStringBuilder.append("Nachname: ");
        toStringBuilder.append(this.getLastName());
        toStringBuilder.append("\n");
        toStringBuilder.append("Email: ");
        toStringBuilder.append(this.getEmail());
        toStringBuilder.append("\n");
        toStringBuilder.append("ID: ");
        toStringBuilder.append(this.getUserId());
        toStringBuilder.append("\n");
        toStringBuilder.append("PasswortHash: ");
        toStringBuilder.append(this.getPassword());
        toStringBuilder.append("\n");
        toStringBuilder.append("##################################################");

       return toStringBuilder.toString();
    }
}