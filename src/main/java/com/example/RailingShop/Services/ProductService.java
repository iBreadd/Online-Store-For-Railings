package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Exceptions.ResourceNotFoundException;
import com.example.RailingShop.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product>getSortedProducts(String sortBy, String sortDirection){
        List<Product> products= (List<Product>) productRepository.findAll();

        Comparator<Product>comparator = switch (sortBy){
            case "name"-> Comparator.comparing(Product::getName);
            case "price"-> Comparator.comparing(Product::getPrice);
            case "expireIn"-> Comparator.comparing(Product::getExpires_in);
            default -> null;
        };
        if(comparator!=null){
            if("desc".equals(sortDirection)){
                comparator=comparator.reversed();
            }
            products.sort(comparator);
        }
        return products;
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void updateProduct(Long id, Product product){
        if(!id.equals(product.getId())){
            product.setId(id);
        }
        productRepository.save(product);
    }

    public Product getProductById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","id",productId));
    }

    public List<Product>searchProducts(Long id, String name, int quantity, BigDecimal priceMin, BigDecimal priceMax){
        if (priceMin!=null){
            priceMin=priceMin.setScale(2, RoundingMode.HALF_UP);
        }
        if (priceMax!=null){
            priceMax=priceMax.setScale(2, RoundingMode.HALF_UP);
        }
        return productRepository.searchProducts(id,name,quantity,priceMin,priceMax);
    }

    public void save(Product product){
        productRepository.save(product);
    }


}
