package com.example.order.services;

import com.example.order.enums.Role;
import com.example.order.exception.OptionalEmpty;
import com.example.order.model.*;
import com.example.order.model.dto.LoginRequestDto;
import com.example.order.model.dto.UserSignUpDto;
import com.example.order.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
@Data
@AllArgsConstructor

public class UserService {
    private final UserRepository userRepository;


    public void signUp(UserSignUpDto userDto){

        if (userRepository.findByUserName(userDto.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken");
        }

        // Validate if the email is unique
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }

        User user = User
                .builder()
                .id(userDto.getEmail())
                .email(userDto.getEmail())
                .userName(userDto.getUserName())
                .address(userDto.getAddress())
                .phone(userDto.getPhone())
                .password(userDto.getPassword())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public User logIn(LoginRequestDto loginDto) throws OptionalEmpty {
        return getUserByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
    }


    public boolean isUserAdmin(String email) throws OptionalEmpty {
        return this.getUserByEmail(email).getRole() == Role.ADMIN;
    }


    public User getUserByUserName(String userName){
        Optional<User> user = userRepository.findByUserName(userName.toLowerCase());
        if (user.isPresent())
            return user.get();
        else try {
            throw new OptionalEmpty("No such user found");
        } catch (OptionalEmpty e) {
            throw new RuntimeException(e);
        }
    }
    public User getUserByUserNameAndPassword(String userName , String password){
        Optional<User> user = userRepository.findByUserNameAndPassword(userName.toLowerCase() , password.toLowerCase());
        if (user.isPresent())
            return user.get();
        else try {
            throw new OptionalEmpty("No such user found");
        } catch (OptionalEmpty e) {
            throw new RuntimeException(e);
        }
    }
    public User getUserByEmailAndPassword(String email , String password) throws OptionalEmpty {
        return userRepository.findByEmailAndPassword(email , password)
                .orElseThrow(() -> new OptionalEmpty("No such user found"));
    }

    public User getUserByEmail(String email) throws OptionalEmpty {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new OptionalEmpty("No such user found"));
    }
}
