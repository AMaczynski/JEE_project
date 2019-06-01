package pl.edu.agh.service;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Singleton
public class BaseService {

    private EntityManager em;

    @PostConstruct
    private void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MysqlPersistenceUnit");
        em = factory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
