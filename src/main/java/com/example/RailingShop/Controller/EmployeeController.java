package com.example.RailingShop.Controller;

import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@RequestMapping("/shop")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public String showEmployees(Model model) {
//        List<Employee> employees = employeeService.getAllEmployees();
//        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/sort")
    public String sortEmployees(@RequestParam String sortBy, Model model) {
//        List<Employee> employees = employeeService.sortEmployees(sortBy);
//        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employee/menu")
    public String showMenuEmployee() {

        var authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authentication!=null&&
                authentication.stream().anyMatch(x-> x.getAuthority().equals("EMPLOYEE"))) {
            return "menu";
        }


        return "access-denied";
    }

}
