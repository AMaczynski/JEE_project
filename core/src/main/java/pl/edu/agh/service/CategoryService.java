package pl.edu.agh.service;

import pl.edu.agh.api.ICategoryService;
import pl.edu.agh.datamodel.Category;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
@Local(ICategoryService.class)
public class CategoryService extends BaseService implements ICategoryService {
    @Override
    public Category addCategory(Category category) {
        EntityManager em = getEntityManager();
        em.persist(category);
        return category;
    }

    @Override
    public boolean deleteCategory(long id) {
        EntityManager em = getEntityManager();
        Category category = em.find(Category.class, id);
        em.remove(category);
        return true;
    }

    @Override
    public Category editCategory(Category category) {
        return null;
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
        CriteriaQuery<Category> all = query.select(root);
        return em.createQuery(all).getResultList();
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
