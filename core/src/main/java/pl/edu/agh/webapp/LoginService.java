package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.faces.bean.ManagedBean;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "LoginService")
public class LoginService {
    private String username;
    private String password;

//    @EJB(lookup="java:app/core-1.0-SNAPSHOT/UserServiceImpl")
//    private UserServiceImpl userService;

    public boolean login() {
//        userService.addUser("artur", "artur123");
        return true;
    }
}
