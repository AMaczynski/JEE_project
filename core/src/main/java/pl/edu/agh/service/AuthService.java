package pl.edu.agh.service;

import pl.edu.agh.api.IAuthService;
import pl.edu.agh.dao.AddressDao;
import pl.edu.agh.dao.UserDao;
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
import static pl.edu.agh.service.Utils.daoToDto;
import static pl.edu.agh.service.Utils.dtoToDao;


@Stateless
@Remote(IAuthService.class)
public class AuthService extends BaseService implements IAuthService {

    @Override
    public User addUser(String login, String password, Address address) {
        if (doesUserExists(login))
            return null;

        AddressDao addressDao = dtoToDao(address);

        byte[] salt = login.getBytes();
        password = hashSha256(password, salt);

        EntityManager em = getEntityManager();
        em.persist(address);

        UserDao newUser = UserDao.builder()
                .login(login)
                .password(password)
                .address(addressDao)
                .build();
        em.persist(newUser);
        em.getTransaction().commit();
        return daoToDto(newUser);
    }

    @Override
    public User authorizeUser(String login, String password) {
        UserDao userDao = findUserByLoginAndPassword(login, password);
        if (userDao == null)
            return null;
        return daoToDto(userDao);
    }

    @Override
    public User changePassword(String login, String oldPassword, String newPassword) {
        UserDao u = findUserByLoginAndPassword(login, oldPassword);
        if (u == null)
            return null;
        else {
            EntityManager em = getEntityManager();

            byte[] salt = login.getBytes();

            newPassword = hashSha256(newPassword, salt);
            u.setPassword(newPassword);
            return daoToDto(em.merge(u));
        }
    }

    private UserDao findUserByLoginAndPassword(String login, String password) {
        EntityManager em = getEntityManager();

        byte[] salt = login.getBytes();
        password = hashSha256(password, salt);

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserDao> query = builder.createQuery(UserDao.class);
        Root<UserDao> root = query.from(UserDao.class);
        Predicate predicateForLogin = builder.equal(root.get("login"), login);
        Predicate predicateForPassword = builder.equal(root.get("password"), password);
        Predicate finalPredicate = builder.and(predicateForLogin, predicateForPassword);
        query.where(finalPredicate);
        UserDao result = null;
        try {
            result = em.createQuery(query).getSingleResult();
        } catch (Exception ignored) {
        }
        return result;
    }

    private boolean doesUserExists(String login) {
        EntityManager em = getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<UserDao> query = builder.createQuery(UserDao.class);
        Root<UserDao> root = query.from(UserDao.class);
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
