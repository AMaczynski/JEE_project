package pl.edu.agh.service;

import pl.edu.agh.api.IUserService;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local
public class UserService extends BaseService implements IUserService {

}
