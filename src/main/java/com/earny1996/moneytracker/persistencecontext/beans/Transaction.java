package com.earny1996.moneytracker.persistencecontext.beans;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    private User transactionUser;
    private Account fromAccount;
    private Account toAccount;
    private String transactionId;
    private LocalDateTime transactionDate;
    private double amount;

    public Transaction(User transactionUser, Account from, Account to, double amount){
        this(transactionUser, from, to, amount, null);
    }

    public Transaction(User transactionUser, Account from, Account to, double amount, LocalDateTime transactionDate){
        this.setTransactionUser(transactionUser);
        this.setFromAccount(from);
        this.setToAccount(to);
        this.setAmount(amount);
        this.setTransactionId();
        this.setTransactionDate(transactionDate);

        fromAccount.subtract(amount);
        toAccount.add(amount);
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    private void setFromAccount(Account fromAccount){
        if(fromAccount == null){
            throw new RuntimeException("FromAccount is invalid");
        }
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    private void setToAccount(Account toAccount){
        if(toAccount == null){
            throw new RuntimeException("ToAccount is invalid");
        }
        this.toAccount = toAccount;
    }

    public double getAmount() {
        return amount;
    }

    private void setAmount(double amount){
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        if(transactionDate != null){
            this.transactionDate = transactionDate;
        } else {
            this.transactionDate = java.time.LocalDateTime.now();
        }
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId() {
        this.transactionId = UUID.randomUUID().toString();
    }

    public User getTransactionUser() {
        return transactionUser;
    }

    public void setTransactionUser(User transactionUser) {
        if(transactionUser == null){
            throw new RuntimeException("Transaction User is invalid.");
        }
        this.transactionUser = transactionUser;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Transaction for ");
        //stringBuilder.append(transactionUser.getFirstName());
        stringBuilder.append(":");
        stringBuilder.append("\n");
        stringBuilder.append(fromAccount.getName());
        stringBuilder.append(" ");
        stringBuilder.append(this.amount);
       // stringBuilder.append(fromAccount.getCurrency().getSymbol());
        stringBuilder.append(" an ");
        stringBuilder.append(toAccount.getName());
        stringBuilder.append(" ");
        stringBuilder.append(amount);
        //stringBuilder.append(toAccount.getCurrency().getSymbol());

        return stringBuilder.toString();
    }
}