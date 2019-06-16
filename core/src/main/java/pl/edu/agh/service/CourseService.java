package pl.edu.agh.service;

import pl.edu.agh.api.ICourseService;
import pl.edu.agh.api.IJMSSender;
import pl.edu.agh.datamodel.Category;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
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

@Stateless
@Remote(ICourseService.class)
public class CourseService extends BaseService implements ICourseService {

    @EJB(lookup = "java:global/core/JMSSender")
    private IJMSSender jmsSender;

    @Override
    public void addCourse(Course course) {
        course.setIsApproved(true);
        EntityManager em = getEntityManager();
        em.persist(course);
        em.getTransaction().commit();
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
        course.setArchived(true);
        em.getTransaction().commit();
        jmsSender.sendMessage("Course " + course.getName() + " has been deleted.");

    }


    @Override
    public void editCourse(Course newCourse) {
        System.out.println("Sending edit course");
        jmsSender.sendMessage("Course " + newCourse.getName() + " has been changed.");

        EntityManager em = getEntityManager();
        Course course = em.find(Course.class, newCourse.getId());
        course.setName(newCourse.getName());
        course.setArchived(newCourse.isArchived());
        course.setCategory(newCourse.getCategory());
        course.setPrize(newCourse.getPrize());
        course.setSize(newCourse.getSize());
    }

    @Override
    public Course queryCourseById(long id) {
        EntityManager em = getEntityManager();
        return em.find(Course.class, id);
    }

    @Override
    public List<Course> queryCourses(boolean approved) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        Predicate predicate = builder.equal(root.get("isApproved"), approved);
        Predicate notArchivedPredicate = builder.equal(root.get("isArchived"), false);
        query.where(predicate, notArchivedPredicate);
        List<Course> courses = em.createQuery(query).getResultList();
        if (courses.isEmpty()) {
            return Collections.emptyList();
        }
        return courses.stream().sorted(Comparator.comparing(e -> e.getCategory().getName())).collect(Collectors.toList());
    }

    @Override
    public List<Course> getTopCourses(int limit) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA");
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        Predicate predicate = builder.equal(root.get("isApproved"), true);
        Predicate notArchivedPredicate = builder.equal(root.get("isArchived"), false);
        query.where(predicate, notArchivedPredicate);
        query.orderBy(builder.desc(root.get("ordered")));
        List<Course> courses = em.createQuery(query).getResultList();
        em.close();
        if (courses.isEmpty()) {
            return Collections.emptyList();
        }
        if (limit > courses.size())
            limit = courses.size();
        return new ArrayList<>(courses.subList(0, limit));
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

    @Override
    public void approveCourses(Course course) {
        course.setIsApproved(true);
        getEntityManager().merge(course);
        getEntityManager().getTransaction().commit();
    }

}
