package com.example.RailingShop;


import com.example.RailingShop.Entity.User.User;

import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Repository.EmployeeRepository;

import com.example.RailingShop.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.yaml.snakeyaml.parser.Parser;

import java.net.http.HttpRequest;

public class UserDetailsServiceImpl implements MyUserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user != null ) {
            return new MyUserDetails(user);
        }
        User userByEmail = userRepository.getUserByEmail(username);
        if (userByEmail!=null) {
            return new MyUserDetails(userByEmail);
        }

        if (user==null&& userByEmail==null){
            Employee employee = employeeRepository.getEmployeeByUsername(username);
            if (employee != null) {
                return new EmployeeDetails(employee);
            }
            Employee employee1 = employeeRepository.getEmployeeById(Long.parseLong(username));
            if (employee1 != null) {
                return new EmployeeDetails(employee1);
            }

        }
      throw new UsernameNotFoundException("Could not find user");
    }


    @Override
    public UserDetails loadUserById(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);


//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
//        Employee employee = employeeRepository.findById(Long.valueOf(employeeId)).get();
//
//
//
//        if (employee == null) {
//            throw new UsernameNotFoundException("Could not find user");
//        }
//
//        return new MyUserDetails(employee);
//
    }
}
