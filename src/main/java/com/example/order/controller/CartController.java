package com.example.order.controller;

import com.example.order.exception.OptionalEmpty;
import com.example.order.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestParam String userName, @RequestParam String bookTitle) throws OptionalEmpty {
        cartService.addToCart(userName, bookTitle);
        return new ResponseEntity<>("Book added to the cart successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteFromCart")
    public ResponseEntity<String> deleteFromCart(@RequestParam String userName, @RequestParam String bookTitle) throws OptionalEmpty {
        cartService.deleteBookFromCart(userName, bookTitle);
        return new ResponseEntity<>("Book removed from the cart successfully", HttpStatus.OK);
    }

}
