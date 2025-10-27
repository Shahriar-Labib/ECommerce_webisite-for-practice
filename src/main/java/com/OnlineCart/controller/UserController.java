package com.OnlineCart.controller;

import com.OnlineCart.model.Cart;
import com.OnlineCart.model.Category;
import com.OnlineCart.model.UserDatas;
import com.OnlineCart.service.CartService;
import com.OnlineCart.service.CategoryService;
import com.OnlineCart.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CartService cartService;

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model)
    {
        if(principal != null)
        {
            String email = principal.getName();
            UserDatas userDatas = userDetailsService.getUserByEmail(email);

            model.addAttribute("user",userDatas);
        }
        List<Category> getAllActiveCategories = categoryService.getAllActiveCategory();
        model.addAttribute("categorys",getAllActiveCategories);

    }

    @GetMapping("/")
    public String home()
    {
        return "user_home";

    }

    @GetMapping("/addCart")
    public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, RedirectAttributes session) {
        Cart saveCart = cartService.saveCart(pid, uid);

        if (ObjectUtils.isEmpty(saveCart)) {
            session.addFlashAttribute("errorMsg", "Product add to cart failed");
        }else {
            session.addFlashAttribute("successMsg", "Product added to cart");
        }
        return "redirect:/product/" + pid;
    }
}
