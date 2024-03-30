package com.example.RailingShop.Controller;

import com.example.RailingShop.DTO.UserLoginDTO;
import com.example.RailingShop.DTO.UserRegistrationDTO;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String customLogut(HttpServletRequest request) throws ServletException
    {
        request.logout();
        return "redirect:/login";
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

    @GetMapping("/user-details")
    @PreAuthorize("isAuthenticated()")
    public String showUserDetails(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("authentication", authentication);

        return "user-details";
    }
}
