package pl.edu.agh.webapp;

import lombok.Data;
import org.primefaces.event.FlowEvent;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean(name = "Menu")
@Data
public class MenuWebService {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    private List<Course> coursesList;
    private List<Course> courses = new ArrayList<>();
    private Course selectedCourse = Course.builder()
            .name("Name")
            .size("Size")
            .prize(0.0)
            .build();

    public List<Course> getCourses() {
        this.courses = courseService.queryCourses(false);
        return this.courses;
    }

    public void approveCourse() {
        coursesList.add(selectedCourse);
        courseService.approveCourses(selectedCourse);
    }

    public List<Course> getCoursesList() {
        this.coursesList = courseService.queryCourses(true);
        return coursesList;
    }

    public void setCoursesList(List<Course> coursesList) {
        this.coursesList = coursesList;
    }

    public String onFlowProcess(FlowEvent event) {
        return event.getNewStep();
    }
}
