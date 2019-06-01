package pl.edu.agh.service;

import pl.edu.agh.api.ICourseService;
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
    public Course addCourse(Course category) {
        EntityManager em = getEntityManager();
        em.persist(category);
        em.getTransaction().commit();
        return em.find(Course.class, category);
    }

    @Override
    public boolean deleteCourse(long id) {
        EntityManager em = getEntityManager();
        Course course = em.find(Course.class, id);
        em.remove(course);
        return true;
    }

    @Override
    public Course editCourse(Course newCourse) {
        return null;
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