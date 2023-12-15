package com.example.order.repository;

import com.example.order.enums.Status;
import com.example.order.model.Order;
import com.example.order.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllByUser(User user);
    List<Order> findAllByStatus(Status status);
}
