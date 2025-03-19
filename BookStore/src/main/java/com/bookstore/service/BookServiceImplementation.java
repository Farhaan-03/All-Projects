package com.bookstore.service;

import com.bookstore.entity.BookEntity;
import com.bookstore.exceptions.BookNotFoundException;
import com.bookstore.model.BookModel;
import com.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImplementation implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public BookModel createBook(BookModel bookModel) {
        BookEntity bookEntity = CustomBeanUtils.bookToEntity(bookModel);
        bookRepository.save(bookEntity);
        return CustomBeanUtils.bookToModel(bookEntity);
    }

    @Override
    public List<BookModel> createBooks(List<BookModel> bookModels) {
        List<BookEntity> bookEntities = CustomBeanUtils.booksToEntities(bookModels);
        bookRepository.saveAll(bookEntities);
        return CustomBeanUtils.booksToModels(bookEntities);
    }

    @Override
    public BookModel findBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        return bookEntity.map(CustomBeanUtils::bookToModel).orElseThrow(() -> new BookNotFoundException("Book not found with given " + id));

    }

    @Override
    public List<BookModel> getAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        return CustomBeanUtils.booksToModels(bookEntities);

    }

    @Override
    public BookModel findBookByTitle(String title) {
        Optional<BookEntity> bookEntity = bookRepository.findByTitle(title);
        return bookEntity.map(CustomBeanUtils::bookToModel).orElseThrow(() -> new BookNotFoundException("Book Not Found with the title: " + title));
    }

    @Override
    public boolean deleteBookById(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBookByTitle(String title) {
        if (bookRepository.existsByTitle(title)) {
            bookRepository.deleteByTitle(title);
            return true;
        }
        return false;
    }


    @Override
    public BookModel updateBookById(Long id, BookModel bookModel) {
        if (bookRepository.existsById(id)) {
            Optional<BookEntity> optional = bookRepository.findById(id);
            if (optional.isPresent()) {
                BookEntity bookEntity = optional.get();
                bookEntity.setAuthor(bookModel.getAuthor());
                bookEntity.setDescription(bookModel.getDescription());
                bookEntity.setStock(bookModel.getStock());
                bookEntity.setPrice(bookModel.getPrice());
                bookEntity.setRating(bookModel.getRating());
                bookEntity.setTitle(bookModel.getTitle());
                bookRepository.save(bookEntity);
                return CustomBeanUtils.bookToModel(bookEntity);
            }
        }
        return null;
    }

    @Override
    public BookModel partiallyUpdateBook(Long id, Map<String, Object> updatedValues) {
        Optional<BookEntity> existingBook = bookRepository.findById(id);

        if (existingBook.isPresent()) {
            BookEntity bookEntity = existingBook.get();

            if (updatedValues.containsKey("title")) {
                bookEntity.setTitle((String) updatedValues.get("title"));
            }
            if (updatedValues.containsKey("author")) {
                bookEntity.setAuthor((String) updatedValues.get("author"));
            }
            if (updatedValues.containsKey("description")) {
                bookEntity.setDescription((String) updatedValues.get("description"));
            }
            if (updatedValues.containsKey("price")) {
                bookEntity.setPrice((double) updatedValues.get("price"));
            }
            if (updatedValues.containsKey("stock")) {
                bookEntity.setStock((int) updatedValues.get("stock"));
            }
            if (updatedValues.containsKey("rating")) {
                bookEntity.setRating((double) updatedValues.get("rating"));
            }

            bookRepository.save(bookEntity);
            return CustomBeanUtils.bookToModel(bookEntity);
        }
        return null;
    }

    @Override
    public List<BookModel> getBooksByRatingRange(double minRating, double maxRating) {
        List<BookEntity> bookEntities = bookRepository.findByRatingBetween(minRating, maxRating);
        return bookEntities.stream()
                .map(CustomBeanUtils::bookToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> getBooksByPriceRange(double minPrice, double maxPrice) {
        List<BookEntity> bookEntities = bookRepository.findByPriceRange(minPrice, maxPrice);
        return bookEntities.stream()
                .map(CustomBeanUtils::bookToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> getBooksByPriceGreaterThan(double minPrice) {
        List<BookEntity> bookEntities = bookRepository.findByPriceGreaterThan(minPrice);
        return bookEntities.stream()
                .map(CustomBeanUtils::bookToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> getBooksByPriceLessThan(double maxPrice) {
        List<BookEntity> bookEntities = bookRepository.findByPriceLessThan(maxPrice);
        return bookEntities.stream()
                .map(CustomBeanUtils::bookToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> getBooksOrderedByRatingDesc() {
        List<BookEntity> bookEntities = bookRepository.findByHighestRating();
        return bookEntities.stream()
                .map(CustomBeanUtils::bookToModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookModel> getBooksByAuthorAndPriceRange(String author, double minPrice, double maxPrice) {
        List<BookEntity> bookEntities = bookRepository.findByAuthorAndPriceRange(author, minPrice, maxPrice);
        return bookEntities.stream()
                .map(CustomBeanUtils::bookToModel)
                .toList();
    }


}
