package pl.edu.agh.service;

import pl.edu.agh.dao.*;
import pl.edu.agh.datamodel.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static AddressDao dtoToDao(Address address) {
        return AddressDao.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .buildingNumber(address.getBuildingNumber())
                .apartmentNumber(address.getApartmentNumber())
                .build();
    }

    public static UserDao dtoToDao(User user) {
        AddressDao address = dtoToDao(user.getAddress());
        return UserDao.builder()
                .login(user.getLogin())
                .address(address)
                .role(user.getRole())
                .build();
    }

    public static CategoryDao dtoToDao(Category category) {
        return CategoryDao.builder()
                .id(category.getId())
                .isArchived(category.isArchived())
                .name(category.getName())
                .build();
    }

    public static CourseDao dtoToDao(Course course) {
        CategoryDao categoryDao = dtoToDao(course.getCategory());
        return CourseDao.builder()
                .category(categoryDao)
                .counter(course.getCounter())
                .id(course.getId())
                .isApproved(course.getIsApproved())
                .isArchived(course.isArchived())
                .name(course.getName())
                .ordered(course.getOrdered())
                .size(course.getSize())
                .build();
    }

    public static List<CourseDao> dtoToDao(List<Course> courses) {
        List<CourseDao> courseDaoList = new ArrayList<>();
        for (Course c: courses) {
            courseDaoList.add(dtoToDao(c));
        }
        return courseDaoList;
    }

    public static OrderDao dtoToDao(Order order) {
        return OrderDao.builder()
                .status(order.getStatus())
                .address(dtoToDao(order.getAddress()))
                .course(dtoToDao(order.getCourse()))
                .date(order.getDate())
                .user(dtoToDao(order.getUser()))
                .id(order.getId())
                .build();
    }

    public static ScheduleDao dtoToDao(Schedule schedule) {
        AddressDao addressDao = dtoToDao(schedule.getAddress());
        UserDao userDao = dtoToDao(schedule.getUser());
        List<CourseDao> courseDao = dtoToDao(schedule.getCourse());
        return ScheduleDao.builder()
                .address(addressDao)
                .course(courseDao)
                .dayOfWeek(schedule.getDayOfWeek())
                .user(userDao)
                .time(schedule.getTime())
                .build();
    }

    public static Address daoToDto(AddressDao addressDao) {
        return Address.builder()
                .city(addressDao.getCity())
                .street(addressDao.getStreet())
                .buildingNumber(addressDao.getBuildingNumber())
                .apartmentNumber(addressDao.getApartmentNumber())
                .build();
    }

    public static User daoToDto(UserDao userDao) {
        Address address = daoToDto(userDao.getAddress());
        return User.builder()
                .login(userDao.getLogin())
                .address(address)
                .role(userDao.getRole())
                .build();
    }

    public static Schedule daoToDto(ScheduleDao scheduleDao) {
        Address addressDao = daoToDto(scheduleDao.getAddress());
        User userDao = daoToDto(scheduleDao.getUser());
        List<Course> courseDao = daoCoursesToDto(scheduleDao.getCourse());
        return Schedule.builder()
                .address(addressDao)
                .course(courseDao)
                .dayOfWeek(scheduleDao.getDayOfWeek())
                .user(userDao)
                .time(scheduleDao.getTime())
                .build();
    }

    public static Category daoToDto(CategoryDao categoryDao) {
        return Category.builder()
                .id(categoryDao.getId())
                .isArchived(categoryDao.isArchived())
                .name(categoryDao.getName())
                .build();
    }

    public static Course daoToDto(CourseDao courseDao) {
        Category categoryDao = daoToDto(courseDao.getCategory());
        return Course.builder()
                .category(categoryDao)
                .counter(courseDao.getCounter())
                .id(courseDao.getId())
                .isApproved(courseDao.getIsApproved())
                .isArchived(courseDao.isArchived())
                .name(courseDao.getName())
                .ordered(courseDao.getOrdered())
                .size(courseDao.getSize())
                .build();
    }


    public static Order daoToDto(OrderDao orderDao) {
        return Order.builder()
                .status(orderDao.getStatus())
                .address(daoToDto(orderDao.getAddress()))
                .course(daoCoursesToDto(orderDao.getCourse()))
                .date(orderDao.getDate())
                .user(daoToDto(orderDao.getUser()))
                .id(orderDao.getId())
                .build();
    }

    public static List<Course> daoCoursesToDto(List<CourseDao> courses) {
        List<Course> courseDaoList = new ArrayList<>();
        for (CourseDao c: courses) {
            courseDaoList.add(daoToDto(c));
        }
        return courseDaoList;
    }

    public static List<Category> daoCategoriesToDto(List<CategoryDao> categoryDaoList) {
        List<Category> courseDaoList = new ArrayList<>();
        for (CategoryDao c: categoryDaoList) {
            courseDaoList.add(daoToDto(c));
        }
        return courseDaoList;
    }

    public static List<Schedule> daoSchedulesToDto(List<ScheduleDao> scheduleDaoList) {
        List<Schedule> courseDaoList = new ArrayList<>();
        for (ScheduleDao c: scheduleDaoList) {
            courseDaoList.add(daoToDto(c));
        }
        return courseDaoList;
    }


    public static List<Order> daoOrdersToDto(List<OrderDao> orderDaoList) {
        List<Order> orders = new ArrayList<>();
        for (OrderDao c: orderDaoList) {
            orders.add(daoToDto(c));
        }
        return orders;
    }
}
