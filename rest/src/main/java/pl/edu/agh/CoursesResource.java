package pl.edu.agh;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoursesResource {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    @GET
    public List<Course> helloWorld() {
        return courseService.queryAllApprovedCourses();
    }

    @GET
    @Path("/category/{categoryId}")
    public List<Course> queryAllCoursesByCategoryName(@PathParam("categoryId") long categoryId) {
        return courseService.queryCourseByCategory(categoryId);
    }
}
