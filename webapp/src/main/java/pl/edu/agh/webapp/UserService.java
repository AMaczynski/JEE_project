package pl.edu.agh.webapp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.agh.datamodel.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ManagedBean(name = "User")
@SessionScoped
public class UserService {

    private User user;

    public boolean isLogged() {
        return user != null;
    }

    public void logout() { user = null; }
}
