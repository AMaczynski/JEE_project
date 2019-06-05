package pl.edu.agh.service;

import pl.edu.agh.api.IUserService;
import pl.edu.agh.datamodel.User;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Stateless
@Local
public class UserServiceImpl implements IUserService {

    private EntityManager em;

    @PostConstruct
    private void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MysqlPersistenceUnit");
        em = factory.createEntityManager();
    }

}
