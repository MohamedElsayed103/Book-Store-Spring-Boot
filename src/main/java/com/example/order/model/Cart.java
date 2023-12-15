package com.example.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
public class Cart {
    @Id
    private String id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Book> books ;

    public Cart(){
        this.books = new ArrayList<>();
        this.id = UUID.randomUUID().toString();
    }

}
