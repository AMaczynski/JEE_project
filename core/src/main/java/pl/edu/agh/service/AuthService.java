package pl.edu.agh.service;

import pl.edu.agh.api.IAuthService;
import pl.edu.agh.datamodel.User;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@Stateless
@Remote(IAuthService.class)
public class AuthService extends BaseService implements IAuthService {

    @Override
    public User addUser(String login, String password) {
        EntityManager em = getEntityManager();

        User newUser = User.builder()
                .login(login)
                .password(password)
                .build();
        em.persist(newUser);
        em.getTransaction().commit();
        return newUser;
    }

    @Override
    public User authorizeUser(String login, String password) {
        EntityManager em = getEntityManager();

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
