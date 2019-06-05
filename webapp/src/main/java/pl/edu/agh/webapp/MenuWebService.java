package pl.edu.agh.webapp;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;

@RequestScoped
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
}
