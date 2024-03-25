package com.example.RailingShop.Repository;

import com.example.RailingShop.Entity.User.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {
}
