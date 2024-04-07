package com.example.RailingShop.Controller;

import com.example.RailingShop.Entity.Products.Product;
import com.example.RailingShop.Entity.User.User;
import com.example.RailingShop.Enums.ProductCategory;
import com.example.RailingShop.MyUserDetails;
import com.example.RailingShop.Repository.UserRepository;
import com.example.RailingShop.Services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;


    @ModelAttribute("categories")
    public List<ProductCategory> getCategories() {
        return Arrays.asList(ProductCategory.values());
    }

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
    public String showDeleteProductConfirmation(@PathVariable(name="id") Long id, Model model) {
        if (productService.hasEmployeeAuthority()) {
            model.addAttribute("id", id);
            return "delete_product";
        }
        return "access-denied";

    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name="id") Long id) {

        if (productService.hasEmployeeAuthority()) {
            productService.deleteProductbyId(id);
            return "redirect:/products";
        }
        return "access-denied";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditProductForm(@PathVariable(name="id")  Long id, Model model) {


        if (productService.hasEmployeeAuthority()) {
            Product product = productService.getProductById(id).orElseThrow();
            model.addAttribute("product", product);
            return "edit_product";
        }
        return "access-denied";

    }

    @GetMapping("/products/buy/{id}")
    public String showBuyProductForm(@PathVariable(name="id")  Long id, Model model){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user1 = authentication.getPrincipal();
        var userdetails = (MyUserDetails) user1;

        var currentUser = userRepository.getUserByUsername(userdetails.getUsername());

        if (productService.buyAuthority()) {
            Product product = productService.getProductById(id).orElseThrow();
            model.addAttribute("product", product);
            model.addAttribute("user", currentUser);
            return "buy_product";
        }
        return "redirect:/products";

    }

    @PostMapping("/products/buy/{id}")
    public String buyProductPost(@ModelAttribute  Product product, @ModelAttribute User user,
                                 @RequestParam Integer quantity, HttpServletRequest request){
        String name = request.getParameter("name");
        Integer quantityNew = Integer.parseInt(request.getParameter("quantityNew"));
          productService.buyProduct(product, user, quantityNew);
        return "redirect:/products";
    }

    @PostMapping("/products/edit/{id}")
    public String updateProduct(@ModelAttribute Product product, Model model) {
        productService.update(product);
//        model.addAttribute("product", product);
        return "redirect:/products";
    }


    @GetMapping("/products")
    public String showProducts(Model model) {
        Iterable<Product> products = productService.findAllProducts();
        model.addAttribute("products", products);
            return "products";

    }


    @GetMapping("/products/add")
    public String showAddProduct(Model model){

        if (productService.hasEmployeeAuthority()) {
            Product product = new Product();
            model.addAttribute("product", product);
            model.addAttribute("categories", getCategories());

            return "add_product";
        }

        return "access-denied";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute Product product){

        try {
            Product productE = productService.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/products";
    }
}
