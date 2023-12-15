package com.example.order.model;

import com.example.order.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;



@Entity
@Table(name = "_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User{
    @Id
    private String email;
    private String userName;
    private String password;
    private String id;
    private String address;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
}
