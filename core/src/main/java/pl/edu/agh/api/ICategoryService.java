package pl.edu.agh.api;

import pl.edu.agh.datamodel.Category;

import java.util.List;

public interface ICategoryService {

    public Category addCategory(Category category);

    public boolean deleteCategory(long id);

    public Category editCategory(Category category);

    public Category queryCategoryById(long id);

    public List<Category> queryAllCategories();
}
