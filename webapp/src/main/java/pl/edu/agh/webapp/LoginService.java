package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "LoginService")
public class LoginService {
    private String username;
    private String password;

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    public boolean login() {
        List<Course> courseList = courseService.queryAllCourses();
        return true;
    }

}
