package pl.edu.agh.webapp;

import org.primefaces.event.FlowEvent;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ViewScoped
@ManagedBean(name = "Menu")
public class MenuWebService {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    private List<Course> coursesList;

    public List<Course> getCoursesList() {
        this.coursesList = courseService.queryAllApprovedCourses();
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
}
