package com.example.RailingShop.Controller;

import com.example.RailingShop.Services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

}
