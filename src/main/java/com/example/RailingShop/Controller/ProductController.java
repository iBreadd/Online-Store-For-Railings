package com.example.RailingShop.Controller;

import com.example.RailingShop.Entity.Products.Product;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {


    @PostMapping("/cart/add")
    public String addProductToCart(@RequestParam Long product_id, @RequestParam Integer quantity, Model model) {
//        try {
//            cartService.addProduct(product_id, quantity);
//        } catch (ProductNotFoundException e) {
//            model.addAttribute("errorMessage", "Продуктът не е намерен!");
//            return "product";
//        } catch (NotEnoughQuantityException e) {
//            model.addAttribute("errorMessage", "Недостатъчно количество!");
//            return "product";
//        }

        return "redirect:/cart";
    }

    @GetMapping("/products/user/search")
    public String searchProducts(@RequestParam String searchBy, @RequestParam String searchTerm, Model model) {
        List<Product> products;

//        if (searchBy.equals("category")) {
//            products = productService.findByCategory(searchTerm);
//        } else {
//            products = productService.findByName(searchTerm);
//        }

//        model.addAttribute("products", products);
        return "search";
    }


    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String searchBy,
                                 @RequestParam String searchTerm,
                                 @RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice,
                                 Model model) {
//        List<Product> products = productService.searchProducts(searchBy, searchTerm, minPrice, maxPrice);
//        model.addAttribute("products", products);
        return "search_results";
    }

    @GetMapping("/products/delete/{id}")
    public String showDeleteProductConfirmation(@PathVariable Long id, Model model) {
        model.addAttribute("id", id);
        return "delete_product";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
//        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
//        Product product = productService.getProductById(id);
//        model.addAttribute("product", product);
        var authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authentication!=null&&
                authentication.stream().anyMatch(x-> x.getAuthority().equals("EMPLOYEE"))) {
            return "edit_product";
        }
        return "access-denied";

    }

    @GetMapping("/products")
    public String showProducts(Model model) {
//        List<Product> products = productService.getAllProducts();
//        model.addAttribute("products", products);

        var authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authentication!=null&&
                authentication.stream().anyMatch(x-> x.getAuthority().equals("EMPLOYEE"))) {
            return "products";
        }
        return "access-denied";
    }


    @GetMapping("/products/add")
    public String addProduct(){


        var authentication = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (authentication!=null&&
                authentication.stream().anyMatch(x-> x.getAuthority().equals("EMPLOYEE"))) {
            return "add_product";
        }

        return "access-denied";
    }
}
