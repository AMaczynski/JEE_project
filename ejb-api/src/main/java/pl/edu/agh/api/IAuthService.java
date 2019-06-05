package pl.edu.agh.api;

import pl.edu.agh.datamodel.User;


public interface IAuthService {

    User addUser(String login, String password);

    User authorizeUser(String login, String password);
}
