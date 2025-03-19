package com.bookstore.service;

import com.bookstore.model.BookModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookService {
    BookModel createBook(BookModel bookModel);

    List<BookModel> createBooks(List<BookModel> bookModels);

    BookModel findBookById(Long id);

    List<BookModel> getAllBooks();

    BookModel findBookByTitle(String title);

    boolean deleteBookById(Long id);

    BookModel updateBookById(Long id, BookModel bookModel);

    BookModel partiallyUpdateBook(Long id, Map<String, Object> updatedValues);

    List<BookModel> getBooksByRatingRange(double minRating, double maxRating);

    List<BookModel> getBooksByPriceRange(double minPrice, double maxPrice);

    List<BookModel> getBooksByPriceGreaterThan(double minPrice);

    List<BookModel> getBooksByPriceLessThan(double maxPrice);

    List<BookModel> getBooksOrderedByRatingDesc();

    List<BookModel> getBooksByAuthorAndPriceRange(String author, double minPrice, double maxPrice);

    boolean deleteBookByTitle(String title);
}
