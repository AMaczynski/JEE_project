package pl.edu.agh.api;

import pl.edu.agh.datamodel.Course;

import java.util.List;

public interface ICourseService {

    public Course addCourse(Course course);

    public boolean deleteCourse(long id);

    public Course editCourse(Course newCourse);

    public Course queryCourseById(long id);

    public List<Course> queryAllCourses();

    public List<Course> queryCourseByCategory(long categoryId);

}
