package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.IOrderService;
import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Order;
import pl.edu.agh.datamodel.Schedule;
import pl.edu.agh.datamodel.User;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.MethodExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pl.edu.agh.webapp.Utils.statusToString;

@ManagedBean(name = "OrderManage")
@Data
@ViewScoped
public class OrderManagementWebService {

    @ManagedProperty(value = "#{User}")
    private UserService userService;

    @ManagedProperty(value = "#{Cart}")
    private CartService cartService;

    @EJB(lookup = "java:global/core/OrderService")
    private IOrderService orderService;

    @EJB(lookup = "java:global/core/ScheduleService")
    private IScheduleService scheduleService;

    private Date dateTo;
    private Date dateFrom;
    private Order selectedOrder;

    private List<Order> orders;

    private int orderMode = 0;
    private List<DayOfWeek> selectedDays = null;
    private List<DayOfWeek> dayOfWeeks = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (userService.isLogged()) {
            if (userService.isCook())
                orders = orderService.getOrdersWithStatusRange(Const.ORDER_PLACED, Const.ORDER_PREPARING);
            else if (userService.isDriver())
                orders = orderService.getOrdersWithStatusRange(Const.ORDER_PREPARED, Const.ORDER_DELIVERING);
        }
    }

    public String status(Order order) {
        return statusToString(order.getStatus());
    }

    public String nextStatus(Order order) {
        return statusToString(order.getStatus()+1);
    }

    public void proceed() {
        long id = (long) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("order");
        orderService.proceedOrder(id);
    }
}
