package com.example.RailingShop.Repository;

import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Entity.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends CrudRepository<Employee,Long> {

    public Employee getEmployeeById(@Param("id") Long id);
    public Employee getEmployeeByUsername(@Param("username") String username);
}
