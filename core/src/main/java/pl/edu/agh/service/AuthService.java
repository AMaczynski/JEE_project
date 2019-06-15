package pl.edu.agh.service;

import pl.edu.agh.api.IAuthService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.User;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static pl.edu.agh.service.Security.hashSha256;


@Stateless
@Remote(IAuthService.class)
public class AuthService extends BaseService implements IAuthService {

    @Override
    public User addUser(String login, String password, Address address) {
        if (doesUserExists(login))
            return null;

        byte[] salt = login.getBytes();
        password = hashSha256(password, salt);

        EntityManager em = getEntityManager();
        em.persist(address);

        User newUser = User.builder()
                .login(login)
                .password(password)
                .address(address)
                .build();
        em.persist(newUser);
        em.getTransaction().commit();
        return newUser;
    }

    @Override
    public User authorizeUser(String login, String password) {
        EntityManager em = getEntityManager();

        byte[] salt = login.getBytes();
        password = hashSha256(password, salt);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicateForLogin = builder.equal(root.get("login"), login);
        Predicate predicateForPassword = builder.equal(root.get("password"), password);
        Predicate finalPredicate = builder.and(predicateForLogin, predicateForPassword);
        query.where(finalPredicate);
        User result = null;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (Exception ignored) {
        }
        return result;
    }

    @Override
    public User changePassword(String login, String oldPassword, String newPassword) {
        User u = authorizeUser(login, oldPassword);
        if (u == null)
            return null;
        else {
            EntityManager em = getEntityManager();

            byte[] salt = login.getBytes();

            newPassword = hashSha256(newPassword, salt);
            u.setPassword(newPassword);
            return em.merge(u);
        }
    }

    private boolean doesUserExists(String login) {
        EntityManager em = getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicateForLogin = builder.equal(root.get("login"), login);
        query.where(predicateForLogin);
        try {
            em.createQuery(query).getSingleResult();
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }



}
