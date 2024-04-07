package com.example.RailingShop.Repository;

import com.example.RailingShop.Entity.Products.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
