package com.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderModel {

    private Long orderId;
    private List<BookModel> books;
    private CustomerModel customer;
    private int quantity;
    private double totalAmount;
}
