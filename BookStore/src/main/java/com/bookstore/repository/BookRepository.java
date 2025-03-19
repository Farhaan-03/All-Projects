package com.bookstore.repository;

import com.bookstore.entity.BookEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByTitle(String title);

    List<BookEntity> findByRatingBetween(double minRating, double maxRating);

    List<BookEntity> findByPriceGreaterThan(double minPrice);

    List<BookEntity> findByPriceLessThan(double maxPrice);

    /// jpql with named parameter
    @Query("SELECT b FROM BookEntity b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<BookEntity> findByPriceRange(double minPrice, double maxPrice);

    /// jpql with positional parameter
    @Query("SELECT b FROM BookEntity b WHERE b.author = ?1 AND b.price BETWEEN ?2 AND ?3")
    List<BookEntity> findByAuthorAndPriceRange(String author, double minPrice, double maxPrice);


    /// native
    @Query(value = "SELECT * FROM book b ORDER BY b.rating DESC", nativeQuery = true)
    List<BookEntity> findByHighestRating();


    boolean existsByTitle(String title);

    @Transactional
    @Modifying
    void deleteByTitle(String title);
}
