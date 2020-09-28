package com.earny1996.moneytracker.daos.database;

import com.earny1996.moneytracker.beans.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataBase {

    private static DataBase database;

    private DataBase(){

    }

    public static DataBase getInstance(){
        if(database == null){
            database = new DataBase();
        }
        return database;
    }

    public EntityManagerFactory createEntityManagerFactoryByUnitName(String unitName){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unitName);
        return factory;
    }

    public void entity(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("UsersDB");

    }
}
