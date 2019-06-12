package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.datamodel.Course;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "Cart")
@ViewScoped
@Data
public class CartService {

    @ManagedProperty(value = "#{Course}")
    private CourseWebService courseWebService;

    private List<Course> cart = new ArrayList<>();
    private Course selectedCourse;
    public String order() {
        return ("order");
    }

    public void add() {
        Course course = courseWebService.getSelectedCourse();
        cart.add(course);
    }

    public void deleteSelectedCourse() {
        cart.remove(selectedCourse);
    }

    public int getCartSize() {
        return cart.size();
    }

}
