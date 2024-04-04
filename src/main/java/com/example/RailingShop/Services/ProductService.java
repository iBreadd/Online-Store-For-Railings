package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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
}
