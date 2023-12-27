package com.example.order.model.dto;

import com.example.order.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookDto {
    private String id;
    private String photo;
    private String title;
    private String author;
    private double price;
    private int             popularity;
    private boolean         availability;
    private List<Category>  categories;
}
