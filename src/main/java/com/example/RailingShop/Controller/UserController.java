package com.example.RailingShop.Controller;

import com.example.RailingShop.DTO.UserLoginDTO;
import com.example.RailingShop.DTO.UserRegistrationDTO;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

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

        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Validated @ModelAttribute UserRegistrationDTO userRegistrationDTO){
        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder().encode(userRegistrationDTO.getPassword()));
        user.setRole(userRegistrationDTO.getRole());
        user.setEnabled(true);

        userRepository.save(user);

        return "Успешна регистрация!";

    }
}
