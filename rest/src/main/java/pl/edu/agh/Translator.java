package pl.edu.agh;

import pl.edu.agh.datamodel.Course;

import java.util.List;
import java.util.Locale;

public class Translator {
    public static List<Course> translateCourses(List<Course> courses, Locale targetLocale) {
        Locale localeGb = new Locale("en", "GB");
        Locale localeDe = new Locale("de", "DE");
        if (localeGb.equals(targetLocale))
            courses.forEach(e -> e.setName(e.getName() + " lovely"));
        if (localeDe.equals(targetLocale))
            courses.forEach(e -> e.setName(e.getName() + " ausgezeichnet"));
        return courses;
    }
}
