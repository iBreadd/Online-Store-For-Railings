package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.Order;
import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.MyUserDetails;
import com.example.RailingShop.Repository.OrderRepository;
import com.example.RailingShop.Repository.ProductRepository;
import com.example.RailingShop.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    public void buyProduct(Product product, User user, Integer quantityNew){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user1 = authentication.getPrincipal();
        var userdetails = (MyUserDetails) user1;

        User currentUser = userRepository.getUserByUsername(userdetails.getUsername());
        if (product.getPrice() <0 || product.getQuantity()<1) {
            throw new RuntimeException();
        }

        if (product.getQuantity()<quantityNew) {
            throw new RuntimeException();
        }

        product.setQuantity(product.getQuantity()-quantityNew);

        productRepository.save(product);

        Order order = new Order();
        order.setTotalPrice(product.getPrice()*quantityNew);
        order.setUser(currentUser);

        orderRepository.save(order);


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


    public List<Product> findProductsByCriteria(Long id, String name, Double minPrice, Double maxPrice, Integer minQuantity) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        if (id != null) {
            query.where(builder.equal(root.get("id"), id));
        }

        if (name != null && !name.isEmpty()) {
            query.where(builder.like(root.get("name"), "%" + name + "%"));
        }

        if (minPrice != null && maxPrice != null) {
            query.where(builder.between(root.get("price"), minPrice, maxPrice));
        }

        if (minQuantity != null) {
            query.where(builder.greaterThanOrEqualTo(root.get("quantity"), minQuantity));
        }

        return entityManager.createQuery(query).getResultList();
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
