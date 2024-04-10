package com.example.RailingShop.Controller;

import com.example.RailingShop.DTO.UserLoginDTO;
import com.example.RailingShop.DTO.UserRegistrationDTO;
import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Enums.ProductCategory;
import com.example.RailingShop.Repository.ProductRepository;
import com.example.RailingShop.Services.ProductService;
import com.example.RailingShop.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;


@Controller
//@RequestMapping("/shop")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @ModelAttribute("categories")
    public List<ProductCategory> getCategories() {
        return Arrays.asList(ProductCategory.values());
    }



    @GetMapping("/user/search/category/food")
    public String searchProductsByCategoryFood(Model model) {
        if (productService.hasUserAuthority()) {
            List<Product> products = productRepository.findByCategoryType(ProductCategory.FOOD);
            model.addAttribute("products", products);
            return "search";
        }
        return "redirect:/products";
    }
    @GetMapping("/user/search/category/drinks")
    public String searchProductsByCategoryDrinks(Model model) {

        if (productService.hasUserAuthority()) {
            List<Product> products = productRepository.findByCategoryType(ProductCategory.DRINKS);
            model.addAttribute("products", products);
            return "search";
        }
        return "redirect:/products";
    }
    @GetMapping("/user/search/category/sanitary")
    public String searchProductsByCategorySanitary(Model model) {

        if (productService.hasUserAuthority()) {
            List<Product> products = productRepository.findByCategoryType(ProductCategory.SANITARY);
            model.addAttribute("products", products);
            return "search";
        }
        return "redirect:/products";
    }

    @GetMapping("/user/search/category/railings")
    public String searchProductsByCategoryRalings(Model model) {

        if (productService.hasUserAuthority()) {
            List<Product> products = productRepository.findByCategoryType(ProductCategory.RAILINGS);
            model.addAttribute("products", products);
            return "search";
        }
        return "redirect:/products";
    }

    @GetMapping("/user/search/category/others")
    public String searchProductsByCategoryOthers(Model model) {

        if (productService.hasUserAuthority()) {
            List<Product> products = productRepository.findByCategoryType(ProductCategory.OTHERS);
            model.addAttribute("products", products);
            return "search";
        }
        return "redirect:/products";
    }

    @GetMapping("/menu")
    public String showMenu() {

        var authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authentication!=null&&
                authentication.stream().anyMatch(x-> x.getAuthority().equals("EMPLOYEE"))) {
            return "menu";
        }


        return "access-denied";
    }
    @PostMapping("/logout")
    public String customLogout(HttpServletRequest request) throws ServletException
    {
        request.logout();
        return "redirect:/login";
    }

//    @GetMapping("/registration")
//    public String showRegistrationForm(Model model){
//        model.addAttribute("user", new UserRegistrationDTO());
//
//        return "registration";
//    }

//    @PostMapping("/registration")
//    public String registerUser(@Validated @ModelAttribute UserRegistrationDTO userRegistrationDTO){
//        User user = new User();
//        user.setUsername(userRegistrationDTO.getUsername());
//        user.setPassword(passwordEncoder().encode(userRegistrationDTO.getPassword()));
//        user.setRole(userRegistrationDTO.getRole());
//        user.setEnabled(true);
//
//        userRepository.save(user);
//
//        return "Успешна регистрация!";
//
//    }

    @GetMapping("/user-details")
    @PreAuthorize("isAuthenticated()")
    public String showUserDetails(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authentication", authentication);

        return "user-details";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new UserRegistrationDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute UserRegistrationDTO user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            User temp = new User();
            temp.setUsername(user.getUsername());
            temp.setPassword(user.getPassword());
            temp.setRole(user.getRole());
            temp.setEnabled(true);
            userService.register(temp);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "registration";
        }

        return "redirect:/login";

    }
}
