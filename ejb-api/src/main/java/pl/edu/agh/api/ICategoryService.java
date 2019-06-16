package pl.edu.agh.api;

import pl.edu.agh.datamodel.Category;

import java.util.List;

public interface ICategoryService {

    Category addCategory(Category category);

    void deleteCategory(long id);

    void editCategory(Category category);

    Category queryCategoryById(long id);

    List<Category> queryAllCategories();

    Category getCategoryByName(String categoryName);
}
