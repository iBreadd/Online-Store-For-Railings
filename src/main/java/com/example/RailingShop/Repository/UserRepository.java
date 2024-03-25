package com.example.RailingShop.Repository;

import com.example.RailingShop.Entity.User.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername (String username);
}

