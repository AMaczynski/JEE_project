package pl.edu.agh;

import pl.edu.agh.api.IAuthService;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @EJB(lookup = "java:global/core/AuthService")
    private IAuthService authService;

    @GET
    public String test() {
        return "OK";
    }


}
