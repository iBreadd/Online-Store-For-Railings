package com.example.RailingShop.Repository;

import com.example.RailingShop.Entity.OrderProduct;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Long> {
}
