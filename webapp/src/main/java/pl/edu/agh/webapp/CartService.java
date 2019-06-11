package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.datamodel.Course;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "Cart")
@SessionScoped
@Data
public class CartService {

    @ManagedProperty(value = "#{Course}")
    private CourseWebService courseWebService;

    private List<Course> cart = new ArrayList<>();

    public String order() {
        return ("order");
    }

    public void add() {
        Course course = courseWebService.getSelectedCourse();
        cart.add(course);
    }

    public int getCartSize() {
        return cart.size();
    }

}
