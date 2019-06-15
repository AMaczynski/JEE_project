package pl.edu.agh.service;

import pl.edu.agh.api.IOrderService;
import pl.edu.agh.dao.AddressDao;
import pl.edu.agh.dao.CourseDao;
import pl.edu.agh.dao.OrderDao;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Order;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

import static pl.edu.agh.service.Utils.daoOrdersToDto;
import static pl.edu.agh.service.Utils.dtoToDao;

@Stateless
@Remote(IOrderService.class)
public class OrderService extends BaseService implements IOrderService {

    // OK
    @Override
    public Order placeOrder(Order order, Address address) {
        EntityManager em = getEntityManager();
        AddressDao addressDao = dtoToDao(address);
        OrderDao orderDao = dtoToDao(order);
        AddressDao userAddress = orderDao.getUser().getAddress();
        if(!userAddress.getCity().equals(addressDao.getCity()) ||
        !userAddress.getStreet().equals(addressDao.getStreet()) ||
                !(userAddress.getBuildingNumber() == addressDao.getBuildingNumber()) ||
                !(userAddress.getApartmentNumber() == addressDao.getApartmentNumber())) {
            em.persist(addressDao);
        } else {
            orderDao.setAddress(userAddress);
        }
        orderDao.setAddress(addressDao);
        em.persist(order);
        em.getTransaction().commit();
        updateOrderCount(order.getCourse());
        return order;
    }

    // OK
    @Override
    public void deleteOrder(long id) {
        EntityManager em = getEntityManager();
        OrderDao category = em.find(OrderDao.class, id);
        em.remove(category);
    }

    // OK
    @Override
    public Order editOrder(Order order) {
        return null;
    }

    // OK
    @Override
    public List<Order> getUserOrders(long userId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<OrderDao> query = builder.createQuery(OrderDao.class);
        Root<OrderDao> root = query.from(OrderDao.class);
        Predicate predicate = builder.equal(root.get("user"), userId);
        query.where(predicate);
        return daoOrdersToDto(em.createQuery(query).getResultList());
    }

    // OK
    @Override
    public List<Order> getUserOrdersInDateRange(long userId, Date dateFrom, Date dateTo) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<OrderDao> query = builder.createQuery(OrderDao.class);
        Root<OrderDao> root = query.from(OrderDao.class);
        Predicate predicate = builder.equal(root.get("user"), userId);
        Predicate predFrom = builder.between(root.get("date"), dateFrom, dateTo);
        System.out.println(dateFrom.toString());
        System.out.println(dateTo.toString());
        Predicate pred = builder.and(predicate, predFrom);
        query.where(pred);
        return daoOrdersToDto(em.createQuery(query).getResultList());
    }

    // OK
    @Override
    public List<Order> getOrdersWithStatusRange(int statusFrom, int statusTo) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<OrderDao> query = builder.createQuery(OrderDao.class);
        Root<OrderDao> root = query.from(OrderDao.class);
        Predicate pred = builder.between(root.get("status"), statusFrom, statusTo);
        query.where(pred);
        return daoOrdersToDto(em.createQuery(query).getResultList());
    }

    // OK
    @Override
    public void proceedOrder(long id) {
        EntityManager em = getEntityManager();
        OrderDao order = em.find(OrderDao.class, id);
        order.setStatus(order.getStatus()+1);
        em.persist(order);
        em.getTransaction().commit();
    }

    // OK
    @Override
    public List<Order> getAllOrders() {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<OrderDao> query = builder.createQuery(OrderDao.class);
        Root<OrderDao> root = query.from(OrderDao.class);
        CriteriaQuery<OrderDao> all = query.select(root);
        return daoOrdersToDto(em.createQuery(all).getResultList());
    }

    // OK
    @Override
    public void cancelOrder(long id) {
        EntityManager em = getEntityManager();
        OrderDao order = em.find(OrderDao.class, id);
        order.setStatus(-1);
        em.persist(order);
        em.getTransaction().commit();
    }

    private void updateOrderCount(List<Course> courses) {
        EntityManager em = getEntityManager();
        for (Course c: courses) {
            CourseDao dbCourse = queryCourseById(c.getId());
            dbCourse.setOrdered(dbCourse.getOrdered()+1);
            em.persist(dbCourse);
            em.getTransaction().commit();
        }
    }


    private CourseDao queryCourseById(long id) {
        EntityManager em = getEntityManager();
        return em.find(CourseDao.class, id);
    }

}
