package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll() {
        List<Category> rootCategories = categoryRepository.findRootCategories();
        return listHierarchialCatgories(rootCategories);
    }

    public List<Category> listHierarchialCatgories(List<Category> rootCategories) {
        List<Category> hierarchialCatgories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchialCatgories.add(Category.copyFull(rootCategory));
            Set<Category> children = rootCategory.getChildren();

            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchialCatgories.add(Category.copyFull(subCategory, name));
                listSubHierarchialCatgories(hierarchialCatgories, subCategory, 1);
            }

        }
        return hierarchialCatgories;
    }

    public void listSubHierarchialCatgories(List<Category> hierarchialCatgories, Category parent, int subLevel) {
        Set<Category> children = parent.getChildren();
        int newSubLevel = subLevel + 1;
        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            hierarchialCatgories.add(Category.copyFull(subCategory, name));
            listSubHierarchialCatgories(hierarchialCatgories, subCategory, newSubLevel);
        }
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();
        Iterable<Category> categoriesInDB = categoryRepository.findAll();

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUsedInForm.add(Category.copyIdAndName(category));

                Set<Category> children = category.getChildren();
                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();
                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
                    listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
                }
            }

        }

        return categoriesUsedInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {

        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newSubLevel; i++) {
                name += "--";
            }
            name = name + subCategory.getName();
            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);

        }
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
