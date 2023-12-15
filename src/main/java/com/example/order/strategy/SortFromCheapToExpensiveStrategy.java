package com.example.order.strategy;

import com.example.order.model.Book;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
@Qualifier("SortFromCheapToExpensiveStrategy")
public class SortFromCheapToExpensiveStrategy implements SortStrategy{
    @Override
    public List<Book> sort(List<Book> books) {
        books.sort(Comparator.comparing(Book::getPrice));
        return books;
    }
}
