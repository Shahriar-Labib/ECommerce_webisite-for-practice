package com.OnlineCart.service;

import com.OnlineCart.model.Product;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    public List<Product> getAllProduct();

    public Boolean deleteProduct(Integer id);

    public Product getProductById(Integer id);
}
