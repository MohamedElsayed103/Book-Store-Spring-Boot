package com.example.order.model;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String photo;
    private String author;
    private double price;
    private int popularity;
    private String category;
    private boolean availability;}
