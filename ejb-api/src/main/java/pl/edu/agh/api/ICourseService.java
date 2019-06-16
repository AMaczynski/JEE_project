package pl.edu.agh.api;

import pl.edu.agh.datamodel.Course;

import java.util.List;

public interface ICourseService {

    void addCourse(Course course);

    Course addNotApprovedCourse(Course course);

    void deleteCourse(long id);

    void editCourse(Course newCourse);

    Course queryCourseById(long id);

    List<Course> queryCourses(boolean approved);

    List<Course> queryCourseByCategory(long categoryId);

    List<Course> getTopCourses(int limit);

    void approveCourses(Course courses);
}
