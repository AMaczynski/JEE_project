package pl.edu.agh.api;

import pl.edu.agh.datamodel.Course;

import java.util.List;

public interface ICourseService {

    public void addCourse(Course course);

    public void deleteCourse(long id);

    public Course editCourse(Course newCourse);

    public Course queryCourseById(long id);

    public List<Course> queryAllCourses();

    public List<Course> queryAllApprovedCourses();

    public List<Course> queryCourseByCategory(long categoryId);

}
