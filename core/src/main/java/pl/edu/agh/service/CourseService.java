package pl.edu.agh.service;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Category;
import pl.edu.agh.datamodel.Course;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@Remote(ICourseService.class)
public class CourseService extends BaseService implements ICourseService {

    @Override
    public Course addCourse(Course course) {
        course.setIsApproved(true);
        EntityManager em = getEntityManager();
        em.persist(course);
        em.getTransaction().commit();
        return course;
    }

    @Override
    public Course addNotApprovedCourse(Course course) {
        course.setIsApproved(false);
        EntityManager em = getEntityManager();
        if (em.find(Category.class, course.getCategory().getId()) == null) {
            em.persist(course.getCategory());
        }
        em.persist(course);
        em.getTransaction().commit();
        return course;
    }

    @Override
    public void deleteCourse(long id) {
        EntityManager em = getEntityManager();
        Course course = em.find(Course.class, id);
        em.remove(course);
        em.getTransaction().commit();
    }

    @Override
    public Course editCourse(Course newCourse) {
        EntityManager em = getEntityManager();
        Course course = em.find(Course.class, newCourse.getId());
        course.setName(newCourse.getName());
        course.setCategory(newCourse.getCategory());
        course.setPrize(newCourse.getPrize());
        course.setSize(newCourse.getSize());
        return em.merge(course);
    }

    @Override
    public Course queryCourseById(long id) {
        EntityManager em = getEntityManager();
        return em.find(Course.class, id);
    }

    @Override
    public List<Course> queryAllCourses() {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        CriteriaQuery<Course> all = query.select(root);
        return em.createQuery(all).getResultList();
    }

    @Override
    public List<Course> queryAllApprovedCourses() {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        Predicate predicate = builder.equal(root.get("isApproved"), true);
        query.where(predicate);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Course> queryCourseByCategory(long categoryId) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);

        Predicate predicate = builder.equal(root.get("category"), categoryId);
        query.where(predicate);
        return em.createQuery(query).getResultList();
    }
}
