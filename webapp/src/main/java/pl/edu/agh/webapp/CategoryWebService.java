package pl.edu.agh.webapp;

import lombok.Data;
import pl.edu.agh.api.ICategoryService;
import pl.edu.agh.datamodel.Category;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@ManagedBean(name = "Category")
@Data
@ViewScoped
public class CategoryWebService {

    @EJB(lookup = "java:global/core/CategoryService")
    private ICategoryService categoryService;

    private String newCategoryName;
    private Category newCategory;

    public String addCategory() {
        newCategory = new Category();
        newCategory.setName(newCategoryName);
        newCategory = categoryService.addCategory(newCategory);
        if (nonNull(newCategory)) {
            return "Success";
        }
        return "Failure";
    }

    public List<String> getAllCategoriesNames() {
        return categoryService.queryAllCategories().stream().map(Category::getName).collect(Collectors.toList());
    }
}
