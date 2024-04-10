package com.example.RailingShop.Repository;


import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Enums.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {
//    public List<Product> searchProducts(@Param("id")Long id, @Param("name")String name, @Param("quantity")int quantity,
//                                 @Param("priceMin")BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax);

    public List<Product> findByCategoryType(ProductCategory productCategory);

}
