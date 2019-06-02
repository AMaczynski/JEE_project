package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.ICategoryService;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Category;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "Course")
@Data
public class CourseWebService {

    @EJB(lookup = "java:global/core_Web2_exploded/CourseService")
    private ICourseService courseService;

    @EJB(lookup = "java:global/core_Web2_exploded/CategoryService")
    private ICategoryService categoryService;

    private String name;
    private String size;
    private double prize;
    private String categoryName;

    public String addCourse() {
        Category category = categoryService.getCategoryByName(categoryName);
        Course course = Course.builder()
                .name(name)
                .size(size)
                .prize(prize)
                .category(category)
                .build();
        courseService.addCourse(course);
        return "Success";
    }
}
