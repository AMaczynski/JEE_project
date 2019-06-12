package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.ICategoryService;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Category;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import static java.util.Objects.nonNull;

@ManagedBean(name = "Course")
@SessionScoped
@Data
public class CourseWebService {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    @EJB(lookup = "java:global/core/CategoryService")
    private ICategoryService categoryService;

    private Course selectedCourse = Course.builder()
            .name("Name")
            .size("Size")
            .prize(0.0)
            .build();
    private String name;
    private double price;
    private String size;
    private String categoryName;

    public String editCourse() {
        if (nonNull(categoryName)) {
            Category category = categoryService.getCategoryByName(categoryName);
            selectedCourse.setCategory(category);
        }
        courseService.editCourse(selectedCourse);
        return "Success";
    }

    public void addCourse() {
        Category category = categoryService.getCategoryByName(categoryName);
        Course newCourse = Course.builder()
                .name(name)
                .prize(price)
                .size(size)
                .category(category)
                .build();
        courseService.addCourse(newCourse);
        FacesMessage msg = new FacesMessage("Successful", "Course successfully added");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void deleteCourse() {
        courseService.deleteCourse(selectedCourse.getId());
    }
}
