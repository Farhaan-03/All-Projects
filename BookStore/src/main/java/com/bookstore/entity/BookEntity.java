package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long isbn;

    private String title;

    private String author;

    private String description;

    private double price;

    private int stock;

    private double rating;

    @ManyToMany(mappedBy = "books")
    @JsonBackReference
    private List<OrderEntity> orders;
}
