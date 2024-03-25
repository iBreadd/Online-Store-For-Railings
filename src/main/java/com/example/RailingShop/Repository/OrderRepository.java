package com.example.RailingShop.Repository;

import com.example.RailingShop.Entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
