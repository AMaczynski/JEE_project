package pl.edu.agh.service;

import pl.edu.agh.api.ICategoryService;
import pl.edu.agh.api.ICourseService;
import pl.edu.agh.dao.CategoryDao;
import pl.edu.agh.dao.CourseDao;
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

import static pl.edu.agh.service.Utils.*;

@Stateless
@Remote(ICategoryService.class)
public class CategoryService extends BaseService implements ICategoryService {

    @EJB(lookup = "java:global/core/CourseService")
    ICourseService iCourseService;

    // OK
    @Override
    public Category addCategory(Category category) {
        CategoryDao categoryDao = dtoToDao(category);

        EntityManager em = getEntityManager();
        em.persist(categoryDao);
        return category;
    }


    // OK
    @Override
    public void deleteCategory(long id) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<CourseDao> query = builder.createQuery(CourseDao.class);
        Root<CourseDao> root = query.from(CourseDao.class);
        Predicate predicate = builder.equal(root.get("category"), id);
        query.where(predicate);
        List<CourseDao> courses =em.createQuery(query).getResultList();
        courses.forEach(e -> e.setArchived(true));
        courses.forEach(e -> iCourseService.editCourse(daoToDto(e)));

        CategoryDao category = em.find(CategoryDao.class, id);
        category.setArchived(true);
    }

    // OK
    @Override
    public Category editCategory(Category category) {
        EntityManager em = getEntityManager();
        CategoryDao categoryDao = em.find(CategoryDao.class, category.getId());
        categoryDao.setName(category.getName());
        categoryDao.setArchived(category.isArchived());
        em.merge(categoryDao);
        em.getTransaction().commit();
        return category;
    }

    // OK
    @Override
    public Category queryCategoryById(long id) {
        EntityManager em = getEntityManager();
        return daoToDto(em.find(CategoryDao.class, id));
    }

    // OK
    @Override
    public List<Category> queryAllCategories() {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<CategoryDao> query = builder.createQuery(CategoryDao.class);
        Root<CategoryDao> root = query.from(CategoryDao.class);
        Predicate notArchivedPredicate = builder.equal(root.get("isArchived"), false);
        query.where(notArchivedPredicate);
        return daoCategoriesToDto(em.createQuery(query).getResultList());
    }

    // OK
    @Override
    public Category getCategoryByName(String categoryName) {
        EntityManager em = getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<CategoryDao> query = builder.createQuery(CategoryDao.class);
        Root<CategoryDao> root = query.from(CategoryDao.class);
        Predicate predicate = builder.equal(root.get("name"), categoryName);
        query.where(predicate);
        return daoToDto(em.createQuery(query).getSingleResult());
    }
}
