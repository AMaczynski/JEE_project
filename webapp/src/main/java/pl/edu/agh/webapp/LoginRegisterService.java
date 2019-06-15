package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.api.IAuthService;
import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.User;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "LoginRegisterService")
@ViewScoped
public class LoginRegisterService {
    private String currentPassword;
    private String newPassword;
    private String inputLogin;
    private String inputPassword;
    private Address address = new Address();

    @ManagedProperty(value = "#{User}")
    private UserService userService;

    @EJB(lookup = "java:global/core/AuthService")
    private IAuthService authService;

    public void login() {
        User u = authService.authorizeUser(inputLogin, inputPassword);
        if (u != null) {
            userService.setUser(u);
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "Wrong login and/or password"));
        }
    }


    public void register() {
        User u = authService.addUser(inputLogin, inputPassword, address);
        FacesContext context = FacesContext.getCurrentInstance();
        if (u != null) {
            context.addMessage(null, new FacesMessage("Successful", "Registration success"));
        } else {
            context.addMessage(null, new FacesMessage("Error", "Username taken"));
        }
    }

    public void changePassword() {
        FacesContext context = FacesContext.getCurrentInstance();

        User u = authService.changePassword(userService.getUser().getLogin(), currentPassword, newPassword);
        if (u != null) {
            userService.setUser(u);
            context.addMessage(null, new FacesMessage("Successful", "Password change success"));
        }
        else {
            context.addMessage(null, new FacesMessage("Error", "Your current password doesnt match"));
        }
    }
}
