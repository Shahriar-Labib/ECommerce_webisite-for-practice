package com.OnlineCart.repository;

import com.OnlineCart.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    // Spring Data derived query should be named 'existsBy...' to be recognized
    public Boolean existsByName(String name);

    public List<Category> findByIsActiveTrue();
}
