package pl.edu.agh.api;

import pl.edu.agh.datamodel.Course;

import java.util.List;

public interface ICourseService {

    Course addCourse(Course course);

    Course addNotApprovedCourse(Course course);

    void deleteCourse(long id);

    Course editCourse(Course newCourse);

    Course queryCourseById(long id);

    List<Course> queryCourses(boolean approved);

    List<Course> queryCourseByCategory(long categoryId);

    void approveCourses(Course courses);
}
