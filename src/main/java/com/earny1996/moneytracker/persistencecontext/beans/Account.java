package com.earny1996.moneytracker.persistencecontext.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "accounts")
public class Account  extends AbstractBean implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AccountIdGenerator")
    @SequenceGenerator(name = "AccountIdGenerator", initialValue = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "balance")
    private double balance;

    @Column(name = "currencycode")
    private String currencyCode;

    @ManyToOne
    private User user;

    public Account(){
        
    }

    public Account(String name, double balance, String currencyCode, User user){
        this(null, name, balance, currencyCode, user);
    }

    public Account(Long id, String name, double balance, String currencyCode, User user){
        this.setBalance(balance);
        this.setName(name);
        this.setCurrency(currencyCode);
        this.setUser(user);

        if(id == null){
           //id = generateId();
        }

        this.setId(id);
    }

    public Long getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    private void setName(String name){
        if(name != null && name.length() > 0){
            this.name = name;
        } else {
            throw new RuntimeException("Invalid name for Account");
        }
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public User getUser(){
        return this.user;
    }

    private void setCurrency(String currencyCode){
        this.currencyCode = currencyCode;
    }

    public double getBalance(){
        return this.balance;
    }

    public void setBalance(double amount){
        this.balance = amount;
    }

    private void setUser(User user){
        this.user = user;
    }

    private void setId(Long id) {
        this.id = id;
    }
}