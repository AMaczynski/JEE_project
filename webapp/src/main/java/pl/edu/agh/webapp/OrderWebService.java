package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.IOrderService;
import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Order;
import pl.edu.agh.datamodel.Schedule;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static pl.edu.agh.webapp.Utils.statusToString;

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

    @EJB(lookup = "java:global/core/ScheduleService")
    private IScheduleService scheduleService;

    private Address address = new Address();
    private boolean defaultAddress;
    private Date dateTo;
    private Date dateFrom;
    private Order selectedOrder;

    private List<Order> orders;
    private List<Order> loadedOrders;

    private int orderMode = 0;
    private List<DayOfWeek> selectedDays = null;
    private List<DayOfWeek> dayOfWeeks = new ArrayList<>();

    private Date time;

    @PostConstruct
    public void init() {
        dayOfWeeks.add(DayOfWeek.MONDAY);
        dayOfWeeks.add(DayOfWeek.TUESDAY);
        dayOfWeeks.add(DayOfWeek.WEDNESDAY);
        dayOfWeeks.add(DayOfWeek.THURSDAY);
        dayOfWeeks.add(DayOfWeek.FRIDAY);
        dayOfWeeks.add(DayOfWeek.SATURDAY);
        dayOfWeeks.add(DayOfWeek.SUNDAY);
        if (userService.isLogged()) {
            Address tempAddress = userService.getUser().getAddress();
            address.setApartmentNumber(tempAddress.getApartmentNumber());
            address.setBuildingNumber(tempAddress.getBuildingNumber());
            address.setStreet(tempAddress.getStreet());
            address.setCity(tempAddress.getCity());
            if (userService.isClient())
                orders = orderService.getUserOrders(userService.getUser().getId());
            else if (userService.isCook())
                orders = orderService.getOrdersWithStatusRange(Const.ORDER_PLACED, Const.ORDER_PREPARING);
            else if (userService.isDriver())
                orders = orderService.getOrdersWithStatusRange(Const.ORDER_PREPARED, Const.ORDER_DELIVERING);
            else if (userService.isManager())
                orders = orderService.getAllOrders();
        }
    }

    public void changeDefaultAddress() {
        this.defaultAddress = !this.defaultAddress;
    }

    public boolean isOneTime() {
        return orderMode == 0;
    }

    public boolean isScheduled() {
        return orderMode == 1;
    }


    public String confirm() {
        if (cartService.getCart().isEmpty()) {
            return "empty cart";
        }
        if (isScheduled()) {
            addScheduledOrder();
            addOneTimeOrder();
        } else {
            addOneTimeOrder();
        }
        cartService.setCart(new ArrayList<>());
        return "orderSuccess";
    }

    public void filterByDates() {
        orders = orderService.getUserOrdersInDateRange(userService.getUser().getId(), dateFrom, dateTo);
    }

    private void addOneTimeOrder() {
        Order order = Order.builder()
                .address(null)
                .course(cartService.getCart())
                .date(new Date())
                .user(userService.getUser())
                .status(0)
                .build();
        orderService.placeOrder(order, address);
    }

    private void addScheduledOrder() {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatter.format(time);
        List<Schedule> schedules = new ArrayList<>();
        for (DayOfWeek dayOfWeek : selectedDays) {
                Schedule schedule = Schedule.builder()
                        .dayOfWeek(dayOfWeek)
                        .time(dateFormatted)
                        .address(address)
                        .course(cartService.getCart())
                        .user(userService.getUser())
                        .build();
                schedules.add(schedule);
        }
        scheduleService.addSchedules(schedules);
    }

    public String status(Order order) {
        return statusToString(order.getStatus());
    }


    public String nextStatus(Order order) {
        return statusToString(order.getStatus()+1);
    }

    public void proceed() {
        long id = (long) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("order");
        Order orderToRemove = null;
        for (Order o : orders) {
            if (o.getId() == id) {
                o.setStatus(o.getStatus() + 1);
                if ((o.getStatus() == Const.ORDER_PREPARED && userService.isCook()) || (o.getStatus() == Const.ORDER_DELIVERED && userService.isDriver()))
                    orderToRemove = o;
                break;
            }
        }
        orders.remove(orderToRemove);
        orderService.proceedOrder(id);
    }

    public void cancel() {
        long id = (long) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("order");
        for (Order o : orders) {
            if (o.getId() == id) {
                o.setStatus(Const.ORDER_CANCELED);
                break;
            }
        }
        orderService.cancelOrder(id);
    }

    public boolean isDisplayProceed() {
        return userService.isCook() || userService.isDriver();
    }

    public boolean shouldDisplayCancel(Order o) {
        return o.getStatus() == Const.ORDER_PLACED && (userService.isManager() || userService.isClient());
    }
}
