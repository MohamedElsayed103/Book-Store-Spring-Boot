package com.example.order.model;

import com.example.order.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private String id;
    private String paymentType;

    @OneToOne
    private User user;

    @OneToMany
    private List<Book> books;

    @Enumerated(EnumType.STRING)
    private Status status;
}
