package pl.edu.agh.impl;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.interfaces.IMenuSOAPService;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(endpointInterface = "pl.edu.agh.interfaces.IMenuSOAPService")
public class MenuSoapService implements IMenuSOAPService {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    @Override
    @WebMethod
    public Course addCourses(Course newCourse) {
        return courseService.addNotApprovedCourse(newCourse);
    }
}
