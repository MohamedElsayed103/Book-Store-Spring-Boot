package com.example.order.strategy;

import com.example.order.model.Book;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SortStrategy {
    List<Book> sort(List<Book> books);
}
