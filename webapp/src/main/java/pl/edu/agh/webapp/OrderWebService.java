package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.IOrderService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Order;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.Date;

@ManagedBean(name = "Order")
@Data
@ViewScoped
public class OrderWebService {
    @ManagedProperty(value = "#{User}")
    private UserService userService;

    @ManagedProperty(value = "#{Cart}")
    private CartService cartService;

    @EJB(lookup = "java:global/core/OrderService")
    private IOrderService orderService;

    private Address address = new Address();

    private int orderMode = 0;
    private int frequency = -1;

    @PostConstruct
    public void init() {
        if (userService.isLogged())
            address = userService.getUser().getAddress();
    }
    public boolean isOneTime() {
        return orderMode == 0;
    }

    public boolean isScheduled() {
        return orderMode == 1;
    }


    public String confirm() {
        Order newOrder = new Order();
        newOrder.setCity(address.getCity());
        newOrder.setStreet(address.getStreet());
        newOrder.setBuildingNumber(address.getBuildingNumber());
        newOrder.setApartmentNumber(address.getApartmentNumber());
        newOrder.setUser(userService.getUser());
        newOrder.setDate(new Date());
        orderService.placeOrder(newOrder, cartService.getCourses());
        return "orderSuccess";
    }
}
