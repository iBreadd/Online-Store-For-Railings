package com.example.RailingShop.Entity;

import com.example.RailingShop.Entity.User.Employee;
import com.example.RailingShop.Entity.User.User;
import jakarta.persistence.*;

import java.beans.Customizer;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price")
    private double totalPrice;
}
