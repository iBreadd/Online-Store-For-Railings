package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee>getSortedEmployees(String sortBy, String sortDirection){
        Iterable<Employee> employeeIterable = employeeRepository.findAll();
        List<Employee> employees = StreamSupport.stream(employeeIterable.spliterator(), false)
                .collect(Collectors.toList());

        Comparator<Employee>comparator=switch (sortBy){
            case "first_name"->Comparator.comparing(Employee::getFirst_name);
            case "last_name"->Comparator.comparing(Employee::getLast_name);
            case "salary"-> Comparator.comparing(Employee::getSalary);
            default -> null;
        };
    if (comparator!=null){
        if("desc".equals(sortDirection)){
            comparator=comparator.reversed();
        }
        employees.sort(comparator);
    }
    return employees;
    }
}
