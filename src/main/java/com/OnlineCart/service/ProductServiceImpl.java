package com.OnlineCart.service;

import com.OnlineCart.model.Product;
import com.OnlineCart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
@Autowired
private ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Boolean deleteProduct(Integer id) {

        Product product = productRepository.findById(id).orElse(null);
        if(!ObjectUtils.isEmpty(product))
        {
            productRepository.delete(product);
            return true;
        }

        return false;
    }

    @Override
    public Product getProductById(Integer id) {

        Product product = productRepository.findById(id).orElse(null);
        return product;
    }
}
