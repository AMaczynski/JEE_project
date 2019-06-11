package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.IOrderService;
import pl.edu.agh.api.IScheduleService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.Order;
import pl.edu.agh.datamodel.Schedule;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Date dateTo;
    private Date dateFrom;
    private Order selectedOrder;

    private List<Order> userOrders;

    private int orderMode = 0;
    private List<DayOfWeek> selectedDays = null;
    private List<DayOfWeek> dayOfWeeks = new ArrayList<>();

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
            address = userService.getUser().getAddress();
            userOrders = orderService.getUserOrders(userService.getUser().getId());
        }
    }

    public boolean isOneTime() {
        return orderMode == 0;
    }

    public boolean isScheduled() {
        return orderMode == 1;
    }


    public String confirm() {
        Address actualAddress = new Address();
        if (isAddressChanged()) {
            actualAddress = Address.builder()
                    .apartmentNumber(address.getApartmentNumber())
                    .buildingNumber(address.getBuildingNumber())
                    .city(address.getCity())
                    .street(address.getStreet())
                    .build();
        } else {
            actualAddress = userService.getUser().getAddress();
        }
        if (isScheduled()) {
            addScheduledOrder(actualAddress);
        } else {
            addOneTimeOrder(actualAddress);
        }
        return "orderSuccess";
    }

    public void filterByDates() {
        userOrders = orderService.getUserOrdersInDateRange(userService.getUser().getId(), dateFrom, dateTo);
    }

    private boolean isAddressChanged() {
        Address userAddress = userService.getUser().getAddress();
        return userAddress.getApartmentNumber() != address.getApartmentNumber() ||
                userAddress.getBuildingNumber() != address.getBuildingNumber() ||
                !userAddress.getCity().equals(address.getCity()) ||
                !userAddress.getStreet().equals(address.getStreet());
    }

    private void addOneTimeOrder(Address address) {
        List<Order> newOrders = new ArrayList<>();
        for (Course course : cartService.getCart()) {
            Order order = Order.builder()
                    .address(address)
                    .course(course)
                    .date(new Date())
                    .user(userService.getUser())
                    .build();
            newOrders.add(order);
        }
        orderService.placeOrder(newOrders);
    }

    private void addScheduledOrder(Address address) {
        List<Schedule> schedules = new ArrayList<>();
        for (DayOfWeek dayOfWeek : selectedDays) {
            for (Course course : cartService.getCart()) {
                Schedule schedule = Schedule.builder()
                        .dayOfWeek(dayOfWeek)
                        .address(address)
                        .course(course)
                        .user(userService.getUser())
                        .build();
                schedules.add(schedule);
            }
        }
        scheduleService.addSchedules(schedules);
    }
}
