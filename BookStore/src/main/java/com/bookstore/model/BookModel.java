package com.bookstore.model;

import com.bookstore.constraintvalidation.NoWhitespaces;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {

    private Long isbn;

    @NoWhitespaces(message = "Title can't be Empty or contains any spaces")
    private String title;

    @NoWhitespaces(message = "Author can't be Empty or contains any spaces")
    private String author;

    @NoWhitespaces(message = "Description can't be Empty or contains any spaces")
    private String description;

    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    private double rating;


}
