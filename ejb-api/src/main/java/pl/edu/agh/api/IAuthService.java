package pl.edu.agh.api;

import pl.edu.agh.datamodel.User;


public interface IAuthService {

    public User addUser(String login, String password);

    public User authorizeUser(String login, String password);
}
