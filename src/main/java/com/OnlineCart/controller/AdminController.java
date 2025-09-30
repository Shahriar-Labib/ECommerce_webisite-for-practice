package com.OnlineCart.controller;

import com.OnlineCart.model.Category;
import com.OnlineCart.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String index()
    {
        return "adminindex";
    }

    @GetMapping("/addproduct")
    public String addproduct()
    {
        return "add_product";
    }

    @GetMapping("/category")
    public String category()
    {
        return "categorey";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
    throws IOException {
        String imageName = "default.jpg";
        if (!file.isEmpty()) {
            // You can save the file to a directory or process it here
            imageName = file.getOriginalFilename();  // For example, get the file's name
            // Optionally save the file to your server here
        }
        category.setImageName(imageName);


        if (categoryService.existsCategory(category.getName())) {
            redirectAttributes.addFlashAttribute("errorMsg", "Category name already exists");
        } else {
            Category check = categoryService.saveCategory(category);
            if (check == null) {
                redirectAttributes.addFlashAttribute("errorMsg", "Not saved, internal server error");
            } else {
                File saveFile =  new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"category_img"+File.separator+file.getOriginalFilename());

                System.out.println(path);
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                redirectAttributes.addFlashAttribute("successMsg", "Saved successfully");
            }
        }
        return "redirect:/admin/category";
    }



}
