package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;

@ManagedBean(name = "Menu")
@Data
public class MenuWebService {

    @EJB(lookup = "java:global/core_Web2_exploded/CourseService")
    private ICourseService courseService;

    private List<Course> coursesList;

    public List<Course> getCoursesList() {
        this.coursesList = courseService.queryAllCourses();
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }
}
