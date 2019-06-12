package pl.edu.agh.service;

import pl.edu.agh.api.ICategoryService;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.datamodel.Category;
import pl.edu.agh.datamodel.Course;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@Remote(ICategoryService.class)
public class CategoryService extends BaseService implements ICategoryService {

    @EJB(lookup = "java:global/core/CourseService")
    ICourseService iCourseService;

    @Override
    public Category addCategory(Category category) {
        EntityManager em = getEntityManager();
        em.persist(category);
        return category;
    }

    @Override
    public void deleteCategory(long id) {
        EntityManager em = getEntityManager();
        List<Course> courses = iCourseService.queryCourseByCategory(id);
        courses.forEach(e -> e.setArchived(true));
        courses.forEach(e -> iCourseService.editCourse(e));

        Category category = em.find(Category.class, id);
        category.setArchived(true);
    }

    @Override
    public Category editCategory(Category category) {
        EntityManager em = getEntityManager();
        em.merge(category);
        em.getTransaction().commit();
        return category;
    }

    @Override
    public Category queryCategoryById(long id) {
        EntityManager em = getEntityManager();
        return em.find(Category.class, id);
    }

    @Override
    public List<Category> queryAllCategories() {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        Predicate notArchivedPredicate = builder.equal(root.get("isArchived"), false);
        query.where(notArchivedPredicate);
        return em.createQuery(query).getResultList();
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);
        Predicate predicate = builder.equal(root.get("name"), categoryName);
        query.where(predicate);
        return em.createQuery(query).getSingleResult();
    }
}
