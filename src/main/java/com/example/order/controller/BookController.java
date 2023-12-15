package com.example.order.controller;

import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Book;
import com.example.order.model.BookDto;
import com.example.order.model.Review;
import com.example.order.model.dto.AddReviewDto;
import com.example.order.model.dto.CreateBookDto;
import com.example.order.services.BookService;
import com.example.order.strategy.SortStrategy;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/sort")
    public ResponseEntity<List<Book>> getSortedBooks(

            @RequestParam(name = "strategy", required = false) String strategy) {

        List<Book> books = bookService.getSortedBooks(strategy);
       return ResponseEntity.ok(books);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody CreateBookDto createBookDto, @RequestParam String userEmail) throws OptionalEmpty {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(createBookDto, userEmail));
    }

    /*@GetMapping("/get/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) throws OptionalEmpty {
        Book book = bookService.viewBookDetails(title);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }*/

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @PutMapping("/edit/{title}")
    public ResponseEntity<Book> editBookDetails(@PathVariable String title,
                                                @RequestBody CreateBookDto book,@RequestParam String userEmail
                                                ) throws OptionalEmpty {
        Book updatedBook = bookService.editBookDetails(title, book, userEmail);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{title}")
    public ResponseEntity<String> deleteBook(@PathVariable String title, @RequestParam String userEmail) throws OptionalEmpty {
        bookService.deleteBook(title, userEmail);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/filterByCategory")
    public ResponseEntity<List<Book>> filterBooksByCategory(@RequestParam String categoryName) {
        try {
            List<Book> filteredBooks = bookService.filterBooksByCategory(categoryName);
            return new ResponseEntity<>(filteredBooks, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeStatus/{title}")
    public ResponseEntity<String> changeBookStatus(@PathVariable String title, @RequestParam boolean availability) throws OptionalEmpty {
        bookService.changeBookStatus(title, availability);
        return new ResponseEntity<>("Book status changed successfully", HttpStatus.OK);
    }


    @GetMapping("/viewDetails/{title}")
    public ResponseEntity<Book> viewBookDetails(@PathVariable String title) throws OptionalEmpty {
        Book book = bookService.viewBookDetails(title);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("{title}/reviews/add")
    public ResponseEntity<String> addReview(
            @PathVariable String title,
            @RequestBody AddReviewDto addReviewDto
    ) throws OptionalEmpty {
        bookService.addReview(addReviewDto, title);
        return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
    }
}
