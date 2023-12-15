package com.example.order.strategy;

import com.example.order.model.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Qualifier("PopularitySortStrategy")
public class PopularitySortStrategy implements SortStrategy{
    @Override
    public List<Book> sort(List<Book> books) {
        // Sorting by popularity (assuming popularity is a field in the Book class)
        books.sort(Comparator.comparing(Book::getPopularity).reversed());
        return books;
    }
}
