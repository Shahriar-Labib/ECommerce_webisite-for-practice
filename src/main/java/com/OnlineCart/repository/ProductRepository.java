package com.OnlineCart.repository;

import com.OnlineCart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {


}
