package com.earny1996.moneytracker.beans;

import java.util.Currency;

public class Account {

    private String name;
    private double balance;
    private Currency currency;

    public Account(String name, String currencyCode){
        this(name, 0, currencyCode);
    }

    public Account(String name, double startbalance, String currencyCode){
        this.setBalance(startbalance);
        this.setName(name);
        this.setCurrency(currencyCode);
    }

    public void subtract(double amount){
        this.balance -= amount;
    }

    public void add(double amount){
        this.balance += amount;
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

    public Currency getCurrency() {
        return currency;
    }

    private void setCurrency(String currencyCode){
        this.currency = Currency.getInstance(currencyCode);
    }

    public double getBalance(){
        return this.balance;
    }

    private void setBalance(double amount){
        this.balance = amount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", currency=" + currency +
                '}';
    }
}