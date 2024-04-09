package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.Order;
import com.example.RailingShop.Repository.OrderProductRepository;
import com.example.RailingShop.Repository.OrderRepository;
import com.example.RailingShop.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<Order>getAllOrders(){
        return (List<Order>) orderRepository.findAll();
    }
}
