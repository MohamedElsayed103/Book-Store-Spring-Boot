package com.example.order.services;


import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Book;
import com.example.order.model.Cart;
import com.example.order.model.User;
import com.example.order.repository.CartRepository;
import com.example.order.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Data
public class CartService {

    private UserService userService;
    private UserRepository userRepository;
    private BookService bookService;
    private CartRepository cartRepository;

    public void addToCart(String userName , String bookTitle) throws OptionalEmpty {
        User user = userService.getUserByUserName(userName);
        Book book = bookService.getBookByTitle(bookTitle);
        Cart cart = cartRepository.findByUser_UserName(userName);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        // Add the book to the cart's list of books
        if (!cart.getBooks().contains(book)) {
            cart.getBooks().add(book);
            // Save the changes to the database
            cartRepository.save(cart);
        }else{

            throw new RuntimeException("book already exists");
        }
    }

    public void deleteBookFromCart(String userName , String bookTitle) throws OptionalEmpty {
        Cart cart = cartRepository.findByUser_UserName(userName);
        Book book = bookService.getBookByTitle(bookTitle);
        cart.getBooks().remove(book);
        cartRepository.save(cart);
    }
}
