package com.OnlineCart.controller;

import com.OnlineCart.Utils.CommonUtil;
import com.OnlineCart.model.Category;
import com.OnlineCart.model.Product;
import com.OnlineCart.model.UserDatas;
import com.OnlineCart.service.CategoryService;
import com.OnlineCart.service.ProductService;
import com.OnlineCart.service.UserDetailsService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @ModelAttribute
    public void getUserDetails(Principal principal,Model model)
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
    public String index(){
        return "index";
    }

    @GetMapping("/signin")
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
    public String products(Model model, @RequestParam(value = "category",defaultValue = "") String category)
    {
        List<Category> categories = categoryService.getAllActiveCategory();
        List<Product> products = productService.getAllActiveProducts(category);

        model.addAttribute("categories",categories);
        model.addAttribute("products",products);
        model.addAttribute("paramValue",category);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable int id,Model model){
      Product productById = productService.getProductById(id);
      model.addAttribute("product",productById);
        return "viewproducts";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDatas user,
                           @RequestParam("img") MultipartFile file, RedirectAttributes redirectAttributes)
            throws IOException
    {
        String imageName = file.isEmpty() ? "default.jpg" : file.getOriginalFilename();
        user.setProfileImage(imageName);

       UserDatas saveUser = userDetailsService.saveUser(user);

        if(!ObjectUtils.isEmpty(saveUser))
        {
            if(!file.isEmpty())
            {
                File saveFile =  new ClassPathResource("static/img").getFile();

                Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"profile_img"+File.separator+file.getOriginalFilename());

                System.out.println(path);
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                redirectAttributes.addFlashAttribute("successMsg", "Registered successfully");

            }
            else{
                redirectAttributes.addFlashAttribute("errorMsg", "Something is wrong");

            }
        }
        return "redirect:/register";
    }

    @GetMapping("/forgot-password")
    public String showForgotPassword()
    {
        return "forgot_password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email,
                                        Model model,
                                        RedirectAttributes session,
                                        HttpServletRequest request
                                            ) throws MessagingException, UnsupportedEncodingException {
        UserDatas user = userDetailsService.getUserByEmail(email);

        if(ObjectUtils.isEmpty(user))
        {
            session.addFlashAttribute("errorMsg","invalid email");
        }
        else{
           String reset_token = UUID.randomUUID().toString();
           userDetailsService.updateUserRestToken(email,reset_token);

          String url = CommonUtil.generateUrl(request) + "/reset-password?token="+reset_token;

           Boolean sendMail = commonUtil.sendMail(url,email);

           if(sendMail)
           {
               session.addFlashAttribute("successMsg","Check your email.. Password reset link is given");
           }
           else {
               session.addFlashAttribute("errorMsg","Something wrong on server ! Email not send");
           }

        }
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPassword(@RequestParam String token,RedirectAttributes session,Model model)
    {

       UserDatas userByToken = userDetailsService.getUserByToken(token);

       if(ObjectUtils.isEmpty(userByToken))
       {
           model.addAttribute("msg","Your link is invalid or expired");
           return "error";
       }
       model.addAttribute("token",token);
        return "reset_password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,RedirectAttributes session,Model model,@RequestParam String password)
    {

        UserDatas userByToken = userDetailsService.getUserByToken(token);
        if(ObjectUtils.isEmpty(userByToken))
        {
            model.addAttribute("errorMsg","Your link is invalid or expired");
            return "error";
        }
        else{
            userByToken.setPassword(passwordEncoder.encode(password));
            userByToken.setResetToken(null);
            userDetailsService.updateUser(userByToken);
            session.addFlashAttribute("successMsg","Password Change Successfully");
            model.addAttribute("msg","Password Change Successfully");
            return "error";
        }

    }




}
