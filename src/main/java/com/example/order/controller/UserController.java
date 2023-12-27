package com.example.order.controller;

import com.example.order.exception.OptionalEmpty;
import com.example.order.model.dto.LoginRequestDto;
import com.example.order.model.User;
import com.example.order.model.dto.UserSignUpDto;
import com.example.order.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUp(@RequestBody UserSignUpDto user) {
        userService.signUp(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> logIn(@RequestBody LoginRequestDto loginDto) throws OptionalEmpty {
        return ResponseEntity.ok(userService.logIn(loginDto));
    }

}
