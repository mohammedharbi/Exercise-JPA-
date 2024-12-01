package com.example.amazoncloneupdated.Service;

import com.example.amazoncloneupdated.Model.Category;
import com.example.amazoncloneupdated.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
    public void addCategory(Category category) {categoryRepository.save(category);}

    public boolean updateCategory(Integer id, Category category) {

        Category oldCategory = categoryRepository.findById(id).get();

        if (oldCategory == null) {return false;
        }else {
            oldCategory.setName(category.getName());
            categoryRepository.save(oldCategory);
            return true;
        }
    }

    public boolean deleteCategory(Integer id) {
        Category oldCategory = categoryRepository.findById(id).get();


        if (oldCategory == null) {return false;
        }else {
            categoryRepository.delete(oldCategory);
            return true;
        }
    }
    }



