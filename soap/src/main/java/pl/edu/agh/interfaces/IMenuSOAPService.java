package pl.edu.agh.interfaces;

import pl.edu.agh.datamodel.Course;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface IMenuSOAPService {

    @WebMethod
    public Course addCourses(Course newCourse);

}
