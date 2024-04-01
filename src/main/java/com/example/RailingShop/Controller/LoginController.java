package com.example.RailingShop.Controller;

import com.example.RailingShop.DTO.UserLoginDTO;
import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/login/user")
    public String showLoginUser(Model model) {
        model.addAttribute("user", new UserLoginDTO());
        return "login_user";
    }

    @GetMapping("/login/employee")
    public String showLoginEmployee(Model model) {
        model.addAttribute("employee", new Employee());


        return "login_employee";
    }

    @PostMapping("/login/employee")
    public String postLoginEmployee(@Valid @ModelAttribute Employee employee){
        var employees = employeeRepository.findAll();


        return "redirect:/employee/menu";
    }
//    var authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
}
