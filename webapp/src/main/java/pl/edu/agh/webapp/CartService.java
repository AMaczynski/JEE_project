package pl.edu.agh.webapp;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;
import pl.edu.agh.datamodel.Course;


@ManagedBean(name = "Cart")
@SessionScoped
public class CartService {
    private List<Course> cart;

    @PostConstruct
    public void init() {
        cart = new ArrayList<>();
    }

    public String summary() {
        return ("summary");
    }

    public String getSummary() { return ("summary"); }

    public void add() {
        Course course = (Course) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("course");
        cart.add(course);
    }


    public List<Course> getCourses() {
        return cart;
    }

    public int getCartSize() {
        return cart.size();
    }

}
