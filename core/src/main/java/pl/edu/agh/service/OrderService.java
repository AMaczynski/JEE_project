package pl.edu.agh.service;

import pl.edu.agh.api.IJMSSender;
import pl.edu.agh.api.IOrderService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Order;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
@Remote(IOrderService.class)
public class OrderService extends BaseService implements IOrderService {

    @EJB(lookup = "java:global/core/JMSSender")
    private IJMSSender jmsSender;

    @Override
    public Order placeOrder(Order order, Address address) {
        EntityManager em = getEntityManager();
        Address userAddress = order.getUser().getAddress();
        if(!userAddress.getCity().equals(address.getCity()) ||
        !userAddress.getStreet().equals(address.getStreet()) ||
                !(userAddress.getBuildingNumber() == address.getBuildingNumber()) ||
                !(userAddress.getApartmentNumber() == address.getApartmentNumber())) {
            em.persist(address);
        } else {
            order.setAddress(userAddress);
        }
        order.setAddress(address);
        em.persist(order);
        em.getTransaction().commit();
        updateOrderCount(order.getCourse());
        return order;
    }

    @Override
    public void deleteOrder(long id) {
        EntityManager em = getEntityManager();
        Order category = em.find(Order.class, id);
        em.remove(category);
    }

    @Override
    public Order editOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> getUserOrders(long userId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Predicate predicate = builder.equal(root.get("user"), userId);
        query.where(predicate);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Order> getUserOrdersInDateRange(long userId, Date dateFrom, Date dateTo) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Predicate predicate = builder.equal(root.get("user"), userId);
        Predicate predFrom = builder.between(root.get("date"), dateFrom, dateTo);
        Predicate pred = builder.and(predicate, predFrom);
        query.where(pred);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Order> getOrdersWithStatusRange(int statusFrom, int statusTo) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Predicate pred = builder.between(root.get("status"), statusFrom, statusTo);
        query.where(pred);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void proceedOrder(long id) {
        EntityManager em = getEntityManager();
        Order order = em.find(Order.class, id);
        order.setStatus(order.getStatus()+1);
        em.persist(order);
        em.getTransaction().commit();
    }

    @Override
    public List<Order> getAllOrders() {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = builder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        CriteriaQuery<Order> all = query.select(root);
        return em.createQuery(all).getResultList();
    }

    @Override
    public void cancelOrder(long id) {
        EntityManager em = getEntityManager();
        Order order = em.find(Order.class, id);
        order.setStatus(-1);
        em.persist(order);
        em.getTransaction().commit();
        jmsSender.sendMessage(order.getUser().getLogin() + ": Order canceled.");

    }

    @Override
    public List<Order> getOrdersByUserAndMonth(int month, long userId) {
        List<Order> orders = getUserOrders(userId);
        List<Order> goodOrders = new ArrayList<>();
        for (Order o : orders) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(o.getDate());
            int oMonth = calendar.get(Calendar.MONTH);
            if (oMonth == month) {
                goodOrders.add(o);
            }
        }
        return goodOrders;
    }

    private void updateOrderCount(List<Course> courses) {
        EntityManager em = getEntityManager();
        for (Course c: courses) {
            Course dbCourse = queryCourseById(c.getId());
            dbCourse.setOrdered(dbCourse.getOrdered()+1);
            em.persist(dbCourse);
            em.getTransaction().commit();
        }
    }

    public Course queryCourseById(long id) {
        EntityManager em = getEntityManager();
        return em.find(Course.class, id);
    }

}
