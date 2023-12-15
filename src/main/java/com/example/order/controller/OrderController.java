package com.example.order.controller;

import com.example.order.enums.Status;
import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Order;
import com.example.order.model.dto.AddOrderDto;
import com.example.order.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/viewAll")
    public ResponseEntity<List<Order>> viewAllOrders(@RequestParam String userEmail) throws OptionalEmpty {
        List<Order> orders = orderService.viewOrders(userEmail);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<String> confirmOrder(@PathVariable String orderId, @RequestParam String userEmail) throws OptionalEmpty {
        orderService.confirmOrder(orderId, userEmail);
        return new ResponseEntity<>("Order confirmed successfully", HttpStatus.OK);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable String orderId, @RequestParam String userEmail) throws OptionalEmpty {
        orderService.cancelOrder(orderId, userEmail);
        return new ResponseEntity<>("Order canceled successfully", HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody AddOrderDto order) throws OptionalEmpty {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/checkStatus/{orderId}")
    public ResponseEntity<Status> checkOrderStatus(@PathVariable String orderId) {
        try {
            Status status = orderService.checkOrderStatus(orderId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/viewPrevious/{userName}")
    public ResponseEntity<List<Order>> viewPreviousOrders(@PathVariable String userName) {
        try {
            List<Order> previousOrders = orderService.viewPreviousOrder(userName);
            return new ResponseEntity<>(previousOrders, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
