package com.example.RailingShop.Controller;

import com.example.RailingShop.DTO.UserRegistrationDTO;
import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/register/employee")
    public String showRegisterEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "register_employee";
    }

    @PostMapping("/register/employee")
    public String registerEmployee(@Valid @ModelAttribute Employee employee, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "register_employee";
        }
        try {
            Employee temp = new Employee();
            temp.setUsername(employee.getUsername());
            temp.setRole(employee.getRole());
            String encryptedPassword= BCrypt.hashpw(employee.getPassword(),BCrypt.gensalt());
            temp.setPassword(encryptedPassword);
            employeeRepository.save(temp);
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register_employee";
        }

        return "redirect:/login/employee";

    }
}
