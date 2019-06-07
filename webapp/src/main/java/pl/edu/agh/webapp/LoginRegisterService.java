package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.api.IAuthService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.User;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "LoginRegisterService")
@ViewScoped
public class LoginRegisterService {
    private String inputLogin;
    private String inputPassword;
    private Address address = new Address();

    @ManagedProperty(value = "#{User}")
    private UserService userService;

    @EJB(lookup = "java:global/core/AuthService")
    private IAuthService authService;

    public String login() {
        User u = authService.authorizeUser(inputLogin, inputPassword);
        if (u != null) {
            userService.setUser(u);
            System.out.println(u.getLogin());
            return "menu";
        }
        return "login";
    }

    public String register() {

        authService.addUser(inputLogin, inputPassword, address);
        return "login";
    }
}
