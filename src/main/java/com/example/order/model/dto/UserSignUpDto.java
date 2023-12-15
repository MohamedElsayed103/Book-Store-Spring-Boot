package com.example.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDto {
    private String email;
    private String userName;
    private String password;
    private String address;
    private String phone;
}
