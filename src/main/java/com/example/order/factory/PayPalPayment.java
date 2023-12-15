package com.example.order.factory;

import com.example.order.model.Order;
import org.springframework.stereotype.Component;


public class PayPalPayment implements PaymentMethod{
    @Override
    public void processPayment(Order order ) {

    }
}
