package pl.edu.agh;

import pl.edu.agh.api.IAuthService;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
