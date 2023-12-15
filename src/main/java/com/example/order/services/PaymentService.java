package com.example.order.services;

import com.example.order.factory.PaymentFactory;
import com.example.order.factory.PaymentMethod;
import com.example.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
    private PaymentFactory paymentMethodFactory;

    public void proceedToPayment(Order order) {
        // Get the payment method based on the order or user information
        PaymentMethod paymentMethod = paymentMethodFactory.createPaymentMethod(order.getPaymentType());

        // Process the payment using the selected method
        paymentMethod.processPayment(order);
    }
}
