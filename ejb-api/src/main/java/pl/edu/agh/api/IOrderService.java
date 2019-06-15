package pl.edu.agh.api;

import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Order;

import java.util.Date;
import java.util.List;

public interface IOrderService {

    Order placeOrder(Order order, Address address);

    void deleteOrder(long id);

    Order editOrder(Order order);

    List<Order> getAllOrders();

    List<Order> getUserOrders(long userId);

    List<Order> getUserOrdersInDateRange(long userId, Date dateFrom, Date dateTo);

    List<Order> getOrdersWithStatusRange(int statusFrom, int statusTo);

    void proceedOrder(long id);

    void cancelOrder(long id);

    List<Order> getOrdersByUserAndMonth(int month, long userId);
}
