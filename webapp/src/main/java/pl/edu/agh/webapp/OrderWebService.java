package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.datamodel.Address;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "Order")
@Data
@ViewScoped
public class OrderWebService {

    @ManagedProperty(value = "#{Course}")
    private CourseWebService courseWebService;

    private Address address = new Address();

    private int orderMode = 0;
    private int frequency = -1;

    public boolean isOneTime() {
        return orderMode == 0;
    }

    public boolean isScheduled() {
        return orderMode == 1;
    }


    public String confirm() {
        return "orderSuccess";
    }
}
