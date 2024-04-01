package com.example.RailingShop;

import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


//public class EmployeeDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Employee employee = employeeRepository.getEmployeeByUsername(username);
//        if (employee == null) {
//            throw new UsernameNotFoundException("Could not find user");
//        }
//
//        return new EmployeeDetails(employee);
//    }
//}
