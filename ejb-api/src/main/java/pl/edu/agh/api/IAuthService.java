package pl.edu.agh.api;

import pl.edu.agh.datamodel.Address;
import pl.edu.agh.datamodel.User;


public interface IAuthService {

    User addUser(String login, String password, Address address);

    User authorizeUser(String login, String password);

    User changePassword(String login, String oldPassword, String newPassword);
}
