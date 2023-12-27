package com.example.order.services;

import com.example.order.enums.Status;
import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Order;
import com.example.order.model.User;
import com.example.order.model.dto.AddOrderDto;
import com.example.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BookService bookService;


    public List<Order> viewOrders(String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail)) throw new RuntimeException("User is not admin");

        return orderRepository.findAllByStatus(Status.PENDING);
    }

    public void confirmOrder(String id, String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail))
            throw new RuntimeException("User is not admin");

        Optional<Order> orderOptional = orderRepository.findById(id);
        orderOptional.ifPresent(order -> {
            if ((order.getStatus()!=Status.PENDING))
                throw new RuntimeException("Order has been handled");
            order.setStatus(Status.CONFIRMED);
            orderRepository.save(order);
        });
    }
    public void cancelOrder(String id, String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail))
            throw new RuntimeException("User is not admin");
        Optional<Order> orderOptional = orderRepository.findById(id);
        orderOptional.ifPresent(order -> {
            if ((order.getStatus()!=Status.PENDING))
                throw new RuntimeException("Order has been handled");
            order.setStatus(Status.CANCELED);
            orderRepository.save(order);
        });
    }


    public Order createOrder(AddOrderDto order) throws OptionalEmpty {
        return orderRepository.save(
                Order.builder()
                        .paymentType(order.getPaymentType())
                        .id(order.getId())
                        .status(Status.PENDING)
                        .user(userService.getUserByEmail(order.getUser()))
                        .books(order.getBooks().stream().map(book -> {
                            try {
                                return bookService.getBookByTitle(book);
                            } catch (OptionalEmpty e) {
                                throw new RuntimeException(e);
                            }
                        }).toList())
                        .build()
        );
    }

    public Status checkOrderStatus(String id){
        Order order = getOrderById(id);
        return order.getStatus();
    }

    public List<Order> viewPreviousOrder(String userName){
        User user = userService.getUserByUserName(userName);
        return orderRepository.findAllByUser(user);
    }


    public Order getOrderById(String id)  {
        Optional<Order> order =orderRepository.findById(id);
        if (order.isPresent())
            return order.get();
        else try {
            throw new OptionalEmpty("No such order found");
        } catch (OptionalEmpty e) {
            throw new RuntimeException(e);
        }
    }
}
