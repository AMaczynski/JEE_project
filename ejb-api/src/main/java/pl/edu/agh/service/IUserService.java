package pl.edu.agh.service;

import pl.edu.agh.datamodel.User;


public interface IUserService {

    public User addUser(String login, String password);
}
