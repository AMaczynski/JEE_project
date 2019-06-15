package pl.edu.agh;

import org.apache.commons.collections.list.TransformedList;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Locale;

@Path("courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoursesResource {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    @GET
    @Path("/category/{categoryId}")
    @Produces("application/json")
    public List<Course> queryAllCoursesByCategoryName(@Context HttpServletRequest request, @PathParam("categoryId") long categoryId) {
        Locale requestLocale = request.getLocale();
        List<Course> ret = courseService.queryCourseByCategory(categoryId);
        System.out.println(requestLocale);
        ret = Translator.translateCourses(ret, requestLocale);
        return ret;
    }

    @GET
    @Path("/category/")
    public List<Course> queryAllCategories(@Context HttpServletRequest request) {
        Locale requestLocale = request.getLocale();
        List<Course> ret = courseService.queryCourses(true);
        System.out.println(requestLocale);
        ret = Translator.translateCourses(ret, requestLocale);
        return ret;
    }
}
