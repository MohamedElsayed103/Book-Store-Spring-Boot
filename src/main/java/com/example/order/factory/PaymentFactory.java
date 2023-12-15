package com.example.order.factory;

import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {
    public PaymentMethod createPaymentMethod(String paymentType) {
        switch (paymentType.toLowerCase()) {
            case "cash":
                return new CashPayment();
            case "creditcard":
                return new CreditCardPayment();
            case "paypal":
                return new PayPalPayment();
            default:
                throw new IllegalArgumentException("Invalid payment type");
        }
    }
}
