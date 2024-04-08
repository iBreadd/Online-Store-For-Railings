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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    public String searchProducts(Model model,
                                 @RequestParam(required = false) Long id,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) Double minPrice,
                                 @RequestParam(required = false) Double maxPrice,
                                 @RequestParam(required = false) Integer minQuantity) {

        List<Product> products = productService.findProductsByCriteria(id, name, minPrice, maxPrice, minQuantity);

        model.addAttribute("products", products);

        return "search";
    }
    @GetMapping("/products/sort")
    public String sortProducts(@RequestParam String sortBy, Model model){

        if (productService.buyAuthority()) {
            Iterable<Product> products = productService.findAllProducts();

            List<Product> productList;
            if (products instanceof List) {
                productList = (List<Product>) products;
            } else {
                productList = new ArrayList<>();
                for (Product product : products) {
                    productList.add(product);
                }
            }

            switch (sortBy){
                case "id":
                    Collections.sort(productList, Comparator.comparing(Product::getId));
                    break;
                case "name":
                    Collections.sort(productList, Comparator.comparing(Product::getName));
                    break;
                case "price":
                    Collections.sort(productList, Comparator.comparing(Product::getPrice));
                    break;
                case "quantity":
                    Collections.sort(productList, Comparator.comparing(Product::getQuantity));
                    break;
            }

            model.addAttribute("sortedProducts", productList);

            return "list_of_products";

        }

        return "redirect:/products";


    }

    @GetMapping("/products/sort-expires")
    public String sortProductsByExpires(Model model) {
        Iterable<Product> products = productService.findAllProducts();
        List<Product> productList = getProductList(products);
        productList.sort(Comparator.comparing(Product::getExpires_in));

        model.addAttribute("products", productList);

        return "search_results";
    }
    @GetMapping("/products/sort-quantity")
    public String sortProductsByQuantity(Model model) {
        Iterable<Product> products = productService.findAllProducts();
        List<Product> productList = getProductList(products);
        productList.sort(Comparator.comparing(Product::getQuantity));

        model.addAttribute("products", productList);

        return "search_results";
    }

    @GetMapping("/products/sort-price")
    public String sortProductsByPrice(Model model) {
        Iterable<Product> products = productService.findAllProducts();
        List<Product> productList = getProductList(products);
        productList.sort(Comparator.comparing(Product::getPrice));

        model.addAttribute("products", productList);

        return "search_results";
    }

    @GetMapping("/products/sort-name")
    public String sortProductsByName(Model model) {
        Iterable<Product> products = productService.findAllProducts();
        List<Product> productList = getProductList(products);
        productList.sort(Comparator.comparing(Product::getName));

        model.addAttribute("products", productList);

        return "search_results";
    }

    @GetMapping("/products/sort-id")
    public String sortProductsById(Model model) {
        Iterable<Product> products = productService.findAllProducts();
        List<Product> productList = getProductList(products);
        productList.sort(Comparator.comparing(Product::getId));

        model.addAttribute("products", productList);

        return "search_results";
    }

    private static List<Product> getProductList(Iterable<Product> products) {
        List<Product> productList;
        if (products instanceof List) {
            productList = (List<Product>) products;
        } else {
            productList = new ArrayList<>();
            for (Product product : products) {
                productList.add(product);
            }
        }
        return productList;
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

        User currentUser = userRepository.getUserByUsername(userdetails.getUsername());

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
