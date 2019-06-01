package pl.edu.agh.service;

import pl.edu.agh.datamodel.User;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


@Stateless
@Local(IUserService.class)
public class UserServiceImpl implements IUserService {

    private EntityManager em;

    @PostConstruct
    private void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MysqlPersistenceUnit");
        em = factory.createEntityManager();
    }

    public User addUser(String login, String password) {
        User newUser = User.builder()
                .login(login)
                .password(password)
                .build();
        em.persist(newUser);
        em.getTransaction().commit();
        return em.find(User.class, newUser);
    }
}
