package com.example.order.services;

import com.example.order.exception.OptionalEmpty;
import com.example.order.model.Book;
import com.example.order.model.BookDto;
import com.example.order.model.Category;
import com.example.order.model.Review;
import com.example.order.model.dto.AddReviewDto;
import com.example.order.model.dto.CreateBookDto;
import com.example.order.repository.BookRepository;
import com.example.order.repository.CategoryRepository;
import com.example.order.repository.ReviewRepository;
import com.example.order.strategy.PopularitySortStrategy;
import com.example.order.strategy.SortFromCheapToExpensiveStrategy;
import com.example.order.strategy.SortFromExpensiveToCheapStrategy;
import com.example.order.strategy.SortStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Data
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ReviewRepository reviewRepository;

//    private final SortStrategy sortingStrategy;


    public List<Book> getSortedBooks( String strategy) {
        List<Book> unsortedBooks = getAllBooks();

        SortStrategy sortStrategy = resolveSortStrategy(strategy);

        return  sortStrategy.sort(unsortedBooks);
    }
    public SortStrategy resolveSortStrategy(String strategy) {
        // Add logic to map the strategy parameter to the corresponding SortStrategy bean
        // You can use a switch or if-else statements to handle different strategies
        // For simplicity, this example assumes the strategy names match the bean names

        switch (strategy) {
            case "popularity":
                return new PopularitySortStrategy();
            case "cheapToExpensive":
                return new SortFromCheapToExpensiveStrategy();
            case "expensiveToCheap":
                return new SortFromExpensiveToCheapStrategy();
            default:
                // Default strategy
                return null;
        }
    }

    public Book addBook(CreateBookDto createBookDto, String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail))
            throw new RuntimeException("User is not admin");

        createBookDto.getCategories().forEach(bookCategory -> {
            try {
                categoryRepository.findByName(bookCategory.getCategoryName())
                        .orElseThrow(() -> new OptionalEmpty("No such category with this name: " + bookCategory.getCategoryName() + "\n pls try again"));
            } catch (OptionalEmpty e) {
                throw new RuntimeException(e);
            }
        });

        Book book = Book.builder()
                .categories(createBookDto.getCategories())
                .title(createBookDto.getTitle())
                .photo(createBookDto.getPhoto())
                .author(createBookDto.getAuthor())
                .id(createBookDto.getId())
                .availability(createBookDto.isAvailability())
                .popularity(createBookDto.getPopularity())
                .price(createBookDto.getPrice())
                .build();

        return bookRepository.save(book);
    }


    public void deleteBook(String title, String userEmail) throws OptionalEmpty {
        if (!userService.isUserAdmin(userEmail))
            throw new RuntimeException("User is not admin");
        Book book = getBookByTitle(title);
        bookRepository.delete(book);
    }


    public Book editBookDetails(String title, CreateBookDto createBookDto , String userEmail) throws OptionalEmpty {

        if (!userService.isUserAdmin(userEmail))
            throw new RuntimeException("User is not admin");

        createBookDto.getCategories().forEach(bookCategory -> {
            try {
                categoryRepository.findByName(bookCategory.getCategoryName())
                        .orElseThrow(() -> new OptionalEmpty("No such category with this id: " + bookCategory.getCategoryName() + "\n pls try again"));
            } catch (OptionalEmpty e) {
                throw new RuntimeException(e);
            }
        });

        Book book = Book.builder()
                .categories(createBookDto.getCategories())
                .title(createBookDto.getTitle())
                .photo(createBookDto.getPhoto())
                .author(createBookDto.getAuthor())
                .id(createBookDto.getId())
                .availability(createBookDto.isAvailability())
                .popularity(createBookDto.getPopularity())
                .price(createBookDto.getPrice())
                .build();

        return bookRepository.save(book);

    }

    public void changeBookStatus(String bookTitle, boolean availability) throws OptionalEmpty {
        Book book = getBookByTitle(bookTitle.toLowerCase());
        book.setAvailability(availability);
    }


    public Book viewBookDetails(String title) throws OptionalEmpty {
        return getBookByTitle(title);
    }


    public List<Book> filterBooksByCategory(String categoryName) {
        return bookRepository.findAllByCategoriesContains(
                categoryRepository.findByName(categoryName).get());
    }


    public Book getBookByTitle(String title) throws OptionalEmpty {
        return bookRepository.findByTitle(title.toLowerCase())
                .orElseThrow(() -> new OptionalEmpty("No such book found"));
    }

    public Book getBookById(String id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent())
            return book.get();
        else try {
            throw new OptionalEmpty("No such book found");
        } catch (OptionalEmpty e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addReview(AddReviewDto addReviewDto, String title) throws OptionalEmpty {
        Book book = getBookByTitle(title);
        book.getReviews().add(
                reviewRepository.save(
                        Review
                                .builder()
                                .id(UUID.randomUUID().toString())
                                .comment(addReviewDto.getComment())
                                .user(userService.getUserByUserName(addReviewDto.getUserName()))
                                .build()
                )
        );
        bookRepository.save(book);
    }
}
