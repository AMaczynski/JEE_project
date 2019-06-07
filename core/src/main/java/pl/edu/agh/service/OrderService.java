package pl.edu.agh.service;

import pl.edu.agh.api.IOrderService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Order;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@Remote(IOrderService.class)
public class OrderService extends BaseService implements IOrderService {

    @Override
    public Order placeOrder(Order order, List<Course> courses) {

        EntityManager em = getEntityManager();
        order.setCourses(courses);
        em.persist(order);
        em.getTransaction().commit();
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
}
