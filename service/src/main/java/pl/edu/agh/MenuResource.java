package pl.edu.agh;

import pl.edu.agh.datamodel.Course;
import pl.edu.agh.service.CourseService;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/course")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {

    @EJB(lookup = "java:app/core-1.0-SNAPSHOT/CourseService")
    private CourseService courseService;

    @GET
    public List<Course> getAllCourses() {
        return null;
    }


}
