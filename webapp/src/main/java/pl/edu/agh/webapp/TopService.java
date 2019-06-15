package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "Top")
@ApplicationScoped
public class TopService {
    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    private List<Course> topCourses;

    @PostConstruct
    public void init() {
        createTop();
    }

    public void createTop() {
        topCourses = courseService.getTopCourses(3);
    }
}
