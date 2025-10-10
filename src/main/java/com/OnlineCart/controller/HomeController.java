package com.OnlineCart.controller;

import com.OnlineCart.model.Category;
import com.OnlineCart.model.Product;
import com.OnlineCart.repository.ProductRepository;
import com.OnlineCart.service.CategoryService;
import com.OnlineCart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @GetMapping("/register")
    public String register()
    {
        return "register";
    }

    @GetMapping("/products")
    public String products(Model model)
    {
        List<Category> categories = categoryService.getAllActiveCategory();
        List<Product> products = productService.getAllActiveProducts();

        model.addAttribute("categories",categories);
        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/product")
    public String product(){
        return "viewproducts";
    }
}
