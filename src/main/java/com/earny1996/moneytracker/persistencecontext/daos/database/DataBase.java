package com.earny1996.moneytracker.persistencecontext.daos.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;

public class DataBase {

    private static DataBase database;
    private EntityManagerFactory emf;

    private DataBase(){
        emf = createEntityManagerFactoryByUnitName("money");
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

    /**
     * https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/3-ways-to-build-a-Hibernate-SessionFactory-in-Java-by-example
     * 
     * @return
     */
    public Session getCurrentSession() {
        // JPA and Hibernate SessionFactory example
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("money");
        EntityManager entityManager = emf.createEntityManager();
        
        // Get the Hibernate Session from the EntityManager in JPA
        Session session = entityManager.unwrap(org.hibernate.Session.class);
        
        return session;
      }

      public EntityManager getEntityManager(){
        EntityManager em = emf.createEntityManager();
        return em;
      }
}
