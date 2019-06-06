package pl.edu.agh.impl;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Course;
import pl.edu.agh.interfaces.IMenuSOAPService;

import javax.ejb.EJB;

public class MenuSoapService implements IMenuSOAPService {

    @EJB(lookup = "java:global/core/CourseService")
    private ICourseService courseService;

    @Override
    public Course addCourses(Course newCourse) {
        return courseService.addCourse(newCourse);
    }
}
