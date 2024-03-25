package com.example.RailingShop.Controller;

import com.example.RailingShop.DTO.UserLoginDTO;
import com.example.RailingShop.DTO.UserRegistrationDTO;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class UserController {
    @Autowired
    private UserService userService;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new UserLoginDTO());
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new UserRegistrationDTO());
        return "shop/registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "shop/registration";
        }
        try {
            userService.register(user);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "shop/registration";
        }

        return "redirect:/shop/login";
    }
}
