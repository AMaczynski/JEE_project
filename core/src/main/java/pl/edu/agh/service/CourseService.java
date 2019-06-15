package pl.edu.agh.service;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.dao.CategoryDao;
import pl.edu.agh.dao.CourseDao;
import pl.edu.agh.datamodel.Category;
import pl.edu.agh.datamodel.Course;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static pl.edu.agh.service.Utils.*;

@Stateless
@Remote(ICourseService.class)
public class CourseService extends BaseService implements ICourseService {

    // OK
    @Override
    public Course addCourse(Course course) {
        course.setIsApproved(true);

        CourseDao courseDao = dtoToDao(course);
        EntityManager em = getEntityManager();
        em.persist(courseDao);
        em.getTransaction().commit();
        return course;
    }

    // OK
    @Override
    public Course addNotApprovedCourse(Course course) {
        course.setIsApproved(false);

        CourseDao courseDao = dtoToDao(course);
        EntityManager em = getEntityManager();
        if (em.find(CategoryDao.class, course.getCategory().getId()) == null) {
            em.persist(courseDao.getCategory());
        }
        em.persist(courseDao);
        em.getTransaction().commit();
        return course;
    }

    // OK
    @Override
    public void deleteCourse(long id) {
        EntityManager em = getEntityManager();
        CourseDao courseDao = em.find(CourseDao.class, id);
        courseDao.setArchived(true);
        em.getTransaction().commit();
    }

    // OK
    @Override
    public Course editCourse(Course newCourse) {
        EntityManager em = getEntityManager();
        CourseDao courseDao = em.find(CourseDao.class, newCourse.getId());
        courseDao.setName(newCourse.getName());
        courseDao.setArchived(newCourse.isArchived());
        courseDao.setCategory(dtoToDao(newCourse.getCategory()));
        courseDao.setPrize(newCourse.getPrize());
        courseDao.setSize(newCourse.getSize());

        return daoToDto(em.merge(courseDao));
    }

    // OK
    @Override
    public Course queryCourseById(long id) {
        EntityManager em = getEntityManager();
        return daoToDto(em.find(CourseDao.class, id));
    }

    // OK
    @Override
    public List<Course> queryCourses(boolean approved) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<CourseDao> query = builder.createQuery(CourseDao.class);
        Root<CourseDao> root = query.from(CourseDao.class);
        Predicate predicate = builder.equal(root.get("isApproved"), approved);
        Predicate notArchivedPredicate = builder.equal(root.get("isArchived"), false);
        query.where(predicate, notArchivedPredicate);
        List<CourseDao> courses = em.createQuery(query).getResultList();
        if (courses.isEmpty()) {
            return Collections.emptyList();
        }
        List<Course> coursesDto = daoCoursesToDto(courses);
        return coursesDto.stream().sorted(Comparator.comparing(e -> e.getCategory().getName())).collect(Collectors.toList());
    }

    // OK
    @Override
    public List<Course> getTopCourses(int limit) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<CourseDao> query = builder.createQuery(CourseDao.class);
        Root<CourseDao> root = query.from(CourseDao.class);
        Predicate predicate = builder.equal(root.get("isApproved"), true);
        Predicate notArchivedPredicate = builder.equal(root.get("isArchived"), false);
        query.where(predicate, notArchivedPredicate);
        query.orderBy(builder.desc(root.get("ordered")));
        List<CourseDao> courses = em.createQuery(query).getResultList();
        em.close();
        if (courses.isEmpty()) {
            return Collections.emptyList();
        }
        if (limit > courses.size())
            limit = courses.size();
        List<Course> coursesDto = daoCoursesToDto(courses);
        return new ArrayList<>(coursesDto.subList(0, limit));
    }


    // OK
    @Override
    public List<Course> queryCourseByCategory(long categoryId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<CourseDao> query = builder.createQuery(CourseDao.class);
        Root<CourseDao> root = query.from(CourseDao.class);

        Predicate predicate = builder.equal(root.get("category"), categoryId);
        query.where(predicate);
        return daoCoursesToDto(em.createQuery(query).getResultList());
    }

    // OK
    @Override
    public void approveCourses(Course course) {
        EntityManager em = getEntityManager();
        CourseDao courseDao = em.find(CourseDao.class, course.getId());

        courseDao.setIsApproved(true);
        System.out.println(courseDao.toString());
        getEntityManager().merge(courseDao);

        getEntityManager().getTransaction().commit();
    }

}
