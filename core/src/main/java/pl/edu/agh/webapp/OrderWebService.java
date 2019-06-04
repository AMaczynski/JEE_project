package pl.edu.agh.webapp;

import lombok.Data;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "Order")
@Data
@ViewScoped
public class OrderWebService {

    @ManagedProperty(value = "#{Course}")
    private CourseWebService courseWebService;

}
