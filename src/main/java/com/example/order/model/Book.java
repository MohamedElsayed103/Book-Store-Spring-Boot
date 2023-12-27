package com.example.order.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {
    @Id
    private String id;
    private String photo;
    private String title;
    private String author;
    private double price;
    private int popularity;
    private boolean availability;

    @ManyToMany
    @JoinTable
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany
    private List<Review> reviews;
}
