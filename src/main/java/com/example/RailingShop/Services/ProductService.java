package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.Order;
import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Repository.OrderRepository;
import com.example.RailingShop.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void buyProduct(Product product, User user, Integer quantityNew){
        if (product.getPrice() <0 || product.getQuantity()<1) {
            throw new RuntimeException();
        }

        if (product.getQuantity()<quantityNew) {
            throw new RuntimeException();
        }
        Product product1 = new Product();
//        product1.setName(product.getName());
        product.setQuantity(product.getQuantity()-quantityNew);
//        product1.setPrice(product.getPrice());
//        product1.setColor(product.getColor());
//        product1.setCategoryType(product.getCategoryType());
//        product1.setExpires_in(product.getExpires_in());
        productRepository.save(product);

        Order order = new Order();
        order.setTotalPrice(product.getPrice()*quantityNew);

        orderRepository.save(order);
//        order.setEmployee(new Employee("ivan", "ivanov", 1500.00));

    }

    public void update(Product product){
        Product existingProduct = productRepository.findById(product.getId()).orElseThrow();

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setColor(product.getColor());
        existingProduct.setExpires_in(product.getExpires_in());

        productRepository.save(existingProduct);
    }
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }
    public void deleteProductbyId(Long id){
        productRepository.deleteById(id);
    }


    public Iterable<Product> findAllProducts(){

        return productRepository.findAll();
    }
    public Product save(Product product) throws Exception {
        if (hasEmployeeAuthority()) {

            return productRepository.save(product);
        }
            throw new Exception("Insufficient permissions to save products");

    }

    public boolean hasEmployeeAuthority() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("EMPLOYEE"));
    }
    public boolean buyAuthority() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("EMPLOYEE") || x.getAuthority().equals("USER"));
    }
}
