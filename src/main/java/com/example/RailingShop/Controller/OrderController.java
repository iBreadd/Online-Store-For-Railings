package com.example.RailingShop.Controller;

import com.example.RailingShop.Entity.Order;
import com.example.RailingShop.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public String showOrders(Model model) {
//        List<Order> orders = orderService.getAllOrders();
//        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/orders/filter")
    public String filterOrders(@RequestParam String filterByStatus, Model model) {
//        List<Order> orders = orderService.filterByStatus(filterByStatus);
//        model.addAttribute("orders", orders);
        return "orders";
    }

    @PostMapping("/orders/change-status/{id}")
    public String changeOrderStatus(@PathVariable Long id, @RequestParam String newStatus) {
//        orderService.changeOrderStatus(id, newStatus);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{id}")
    public String showOrderDetails(@PathVariable Long id, Model model) {
        Optional<Order> order = orderService.getOrderById(id);
        if (order.isPresent()) {
            model.addAttribute("order", order.get());
            return "order_details";
        }
        return "redirect:/products";

    }
}
