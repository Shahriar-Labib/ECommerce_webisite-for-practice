package com.OnlineCart.service;

import com.OnlineCart.model.Category;

import java.util.List;

public interface CategoryService {

    public Category saveCategory(Category category);

    public Boolean existsCategory(String name);

    public List<Category> getAllCategory();

    public Boolean deleteCategory(int id);
}
