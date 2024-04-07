package com.example.RailingShop.Services;

import com.example.RailingShop.Entity.Order;
import com.example.RailingShop.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Optional<Order> getOrderById(Long id){

        return orderRepository.findById(id);
    }
}
