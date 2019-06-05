package pl.edu.agh.service;

import pl.edu.agh.api.IAuthService;
import pl.edu.agh.datamodel.User;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@Stateless
@Local
public class AuthServiceImpl implements IAuthService {

    private EntityManager em;

    @PostConstruct
    private void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MysqlPersistenceUnit");
        em = factory.createEntityManager();
    }

    @Override
    public User addUser(String login, String password) {
        User newUser = User.builder()
                .login(login)
                .password(password)
                .build();
        em.persist(newUser);
        em.getTransaction().commit();
        return em.find(User.class, newUser);
    }

    @Override
    public User authorizeUser(String login, String password) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicateForLogin = builder.equal(root.get("login"), login);
        Predicate predicateForPassword = builder.equal(root.get("login"), login);
        Predicate finalPredicate = builder.and(predicateForLogin, predicateForPassword);
        query.where(finalPredicate);
        return em.createQuery(query).getSingleResult();
    }

}
