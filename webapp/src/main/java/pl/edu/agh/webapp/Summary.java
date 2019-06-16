package pl.edu.agh.webapp;

import lombok.Data;
import org.primefaces.PrimeFaces;
import pl.edu.agh.api.IOrderService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Order;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean(name = "Summary")
@Data
public class Summary {

    @EJB(lookup = "java:global/core/OrderService")
    IOrderService orderService;

    @ManagedProperty(value = "#{User}")
    private UserService userService;

    private List<Order> orders;
    private double sum;
    private int month;

    @PostConstruct
    private void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        month = calendar.get(Calendar.MONTH);
        getOrdersForMonth();
    }

    public void getOrdersForMonth() {
        sum = 0;
        long userId = userService.getUser().getId();
        orders = orderService.getOrdersByUserAndMonth(month, userId);
        for (Order order : orders) {
            for (Course course : order.getCourse()) {
                sum += course.getPrize();
            }
        }
        PrimeFaces.current().ajax().update(":menu:singleDT");
    }

}

