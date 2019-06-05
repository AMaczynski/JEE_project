package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.api.IAuthService;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.datamodel.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "LoginRegisterService")
public class LoginRegisterService {
    private String inputLogin;
    private String inputPassword;

    @EJB(lookup = "java:global/core/AuthService")
    private IAuthService authService;

    public String login() {
        User u = authService.authorizeUser(inputLogin, inputPassword);
        if (u != null) {
            System.out.println(u.getLogin());
            return "menu";
        }
        return "login";
    }

    public String register() {
        authService.addUser(inputLogin, inputPassword);
        return "login";
    }
}
