package pl.edu.agh.api;

import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Order order, List<Course> courses);

    void deleteOrder(long id);

    Order editOrder(Order order);

    List<Order> getUserOrders(long userId);

}
