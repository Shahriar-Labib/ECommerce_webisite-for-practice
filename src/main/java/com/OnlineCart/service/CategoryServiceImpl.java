package com.OnlineCart.service;

import com.OnlineCart.model.Category;
import com.OnlineCart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean existsCategory(String name) {
        // call the Spring Data derived query
        return categoryRepository.existsByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean deleteCategory(int id) {
      Category category = categoryRepository.findById(id).orElse(null);

      if(!ObjectUtils.isEmpty(category))
      {
          categoryRepository.delete(category);
          return true;
      }
        return false;
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = categoryRepository.findById(id).orElse(null);

        return category;
    }
}
