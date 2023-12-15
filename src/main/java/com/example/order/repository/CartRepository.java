package com.example.order.repository;

import com.example.order.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart , String> {
    Cart findByUser_UserName(String email);
}
