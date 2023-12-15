package com.example.order.factory;

import com.example.order.model.Order;
import org.springframework.stereotype.Component;

@Component
public interface PaymentMethod {
    void processPayment(Order order);
}
