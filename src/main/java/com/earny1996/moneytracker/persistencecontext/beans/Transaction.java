package com.earny1996.moneytracker.persistencecontext.beans;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
public class Transaction extends AbstractBean{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    @Column(name = "fkusers")
    private User transactionUser;

    @Column(name = "fkfromaccounts")
    private Account fromAccount;

    @Column(name = "fktoaccounts")
    private Account toAccount;

    @Column(name = "executeddate")
    private LocalDateTime transactionDate;

    @Column(name = "amount")
    private double amount;

    @Column(name = "createddate")
    private LocalDateTime createdDate;

    /* Constructors */

    public Transaction(User transactionUser, Account from, Account to, double amount){
        this(transactionUser, from, to, amount, null);
    }

    public Transaction(User transactionUser, Account from, Account to, double amount, LocalDateTime transactionDate){
        this.setTransactionUser(transactionUser);
        this.setFromAccount(from);
        this.setToAccount(to);
        this.setAmount(amount);
        this.setTransactionDate(transactionDate);
    }

    public Transaction(Long id, User transactionUser, Account from, Account to, double amount, LocalDateTime transactionDate){
        this.setTransactionId(id);
        this.setTransactionUser(transactionUser);
        this.setFromAccount(from);
        this.setToAccount(to);
        this.setAmount(amount);
        this.setTransactionDate(transactionDate);
    }

    /* Getter */

    public Account getFromAccount() {
        return fromAccount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public Long getTransactionId() {
        return this.transactionId;
    }

    public User getTransactionUser() {
        return this.transactionUser;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /* Setter */

    private void setAmount(double amount){
        this.amount = amount;
    }

    public void setTransactionId(Long id) {
        this.transactionId = id;
    }

    private void setToAccount(Account toAccount){
        if(toAccount == null){
            throw new RuntimeException("ToAccount is invalid");
        }
        this.toAccount = toAccount;
    }

    private void setFromAccount(Account fromAccount){
        if(fromAccount == null){
            throw new RuntimeException("FromAccount is invalid");
        }
        this.fromAccount = fromAccount;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        if(transactionDate != null){
            this.transactionDate = transactionDate;
        } else {
            this.transactionDate = java.time.LocalDateTime.now();
        }
    }

    public void setTransactionUser(User transactionUser) {
        if(transactionUser == null){
            throw new RuntimeException("Transaction User is invalid.");
        }
        this.transactionUser = transactionUser;
    }

    protected void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
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