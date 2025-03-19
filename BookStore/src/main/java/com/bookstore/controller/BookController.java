package com.bookstore.controller;

import com.bookstore.error.ErrorApi;
import com.bookstore.model.BookModel;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("books")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/recipes/{id}")
    public ResponseEntity<String> getRecipes(@PathVariable int id) {
        return restTemplate.getForEntity("https://dummyjson.com/recipes/" + id, String.class);

    }


    @PostMapping("/")
    public ResponseEntity<BookModel> createBook(@Valid @RequestBody BookModel bookModel) {
        BookModel savedBook = bookService.createBook(bookModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("info", "Book created successfully")
                .body(savedBook);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BookModel>> createBooks(@Valid @RequestBody List<BookModel> bookModels) {
        List<BookModel> savedBooks = bookService.createBooks(bookModels);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("info", "Books created successfully")
                .body(savedBooks);
    }

    @GetMapping("/search/{id}")
//    @PreAuthorize("hasAuthority('customer') or hasAuthority('admin')")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        BookModel book = bookService.findBookById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Book fetched successfully")
                .body(book);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookModel>> getAllBooks() {
        List<BookModel> bookModels = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched successfully")
                .body(bookModels);
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<?> getBookByTitle(@PathVariable String title) {
        BookModel book = bookService.findBookByTitle(title);

        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Book fetched successfully")
                .body(book);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        boolean isDeleted = bookService.deleteBookById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("info", "Book deleted successfully")
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Book Not Found with given Id " + id, LocalDateTime.now()));
        }
    }

    @DeleteMapping("/title/{title}")
    public ResponseEntity<?> deleteBookByTitle(@PathVariable String title) {
        boolean isDeleted = bookService.deleteBookByTitle(title);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("info", "Book deleted successfully")
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Book Not Found with given Title " + title, LocalDateTime.now()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @RequestBody BookModel bookModel) {
        BookModel updatedBook = bookService.updateBookById(id, bookModel);

        if (updatedBook != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Book updated successfully")
                    .body(updatedBook);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Book Not Found with given Id " + id, LocalDateTime.now()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partiallyUpdateBook(@PathVariable Long id, @RequestBody Map<String, Object> updatesValues) {
        BookModel updatedBook = bookService.partiallyUpdateBook(id, updatesValues);

        if (updatedBook != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Book partially updated successfully")
                    .body(updatedBook);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Book Not Found with given Id " + id, LocalDateTime.now()));
        }
    }


    @GetMapping("/search/rating")
    public ResponseEntity<?> getBooksByRatingRange(
            @RequestParam double minRating,
            @RequestParam double maxRating) {

        List<BookModel> books = bookService.getBooksByRatingRange(minRating, maxRating);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Book Not Found within given rating " + minRating + " and " + maxRating, LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched by rating range successfully")
                .body(books);
    }


    @GetMapping("/search/rating/desc")
    public ResponseEntity<?> getBooksOrderedByRatingDesc() {
        List<BookModel> books = bookService.getBooksOrderedByRatingDesc();
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched successfully by rating in descending order")
                .body(books);
    }


    @GetMapping("/search/price")
    public ResponseEntity<?> getBooksByPriceRange(
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {

        List<BookModel> books = bookService.getBooksByPriceRange(minPrice, maxPrice);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "No books found within given price range " + minPrice + " and " + maxPrice, LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched by price range successfully")
                .body(books);
    }

    @GetMapping("/search/price/greater-than")
    public ResponseEntity<?> getBooksByPriceGreaterThan(@RequestParam double minPrice) {

        List<BookModel> books = bookService.getBooksByPriceGreaterThan(minPrice);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "No books found with price greater Than " + minPrice, LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched by price range successfully")
                .body(books);
    }

    @GetMapping("/search/price/less-than")
    public ResponseEntity<?> getBooksByPriceLessThan(@RequestParam double maxPrice) {

        List<BookModel> books = bookService.getBooksByPriceLessThan(maxPrice);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "No books found with price less Than " + maxPrice, LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched by price range successfully")
                .body(books);
    }


    @GetMapping("/search/author/price-range")
    public ResponseEntity<?> getBooksByAuthorAndPriceRange(
            @RequestParam String author,
            @RequestParam double minPrice,
            @RequestParam double maxPrice) {

        List<BookModel> books = bookService.getBooksByAuthorAndPriceRange(author, minPrice, maxPrice);
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "No books found for author: " + author + " within price range " + minPrice + " and " + maxPrice, LocalDateTime.now()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Books fetched successfully by author and price range")
                .body(books);
    }

}
