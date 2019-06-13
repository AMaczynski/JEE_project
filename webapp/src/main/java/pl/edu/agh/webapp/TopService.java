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
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "Top")
@ApplicationScoped
public class TopService {
    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    private List<Course> courses = new ArrayList<>();
    private List<Course> topCourses;

    @PostConstruct
    public void init() {
        createTop();
    }

    public void createTop() {
        courses.clear();
        courses = courseService.queryAllApprovedCourses();
        Collections.sort(courses, (o1, o2) -> {
            int a = o1.getOrdered();
            int b = o2.getOrdered();
            return Integer.compare(a, b);
        });
        topCourses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = courses.size()-1-i;
            topCourses.add(courses.get(index));
            System.out.println(courses.get(index).getName() + ":" + courses.get(index).getOrdered());
        }
    }
}
