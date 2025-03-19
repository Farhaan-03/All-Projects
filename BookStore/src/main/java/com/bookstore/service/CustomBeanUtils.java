package com.bookstore.service;

import com.bookstore.entity.BookEntity;
import com.bookstore.entity.CustomerEntity;
import com.bookstore.entity.OrderEntity;
import com.bookstore.model.BookModel;
import com.bookstore.model.CustomerModel;
import com.bookstore.model.OrderModel;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomBeanUtils {

    public static BookModel bookToModel(BookEntity bookEntity) {
        BookModel bookModel = new BookModel();
        BeanUtils.copyProperties(bookEntity, bookModel);
        return bookModel;
    }

    public static BookEntity bookToEntity(BookModel bookModel) {
        BookEntity bookEntity = new BookEntity();
        BeanUtils.copyProperties(bookModel, bookEntity);
        return bookEntity;
    }

    public static List<BookEntity> booksToEntities(List<BookModel> bookModels) {
        List<BookEntity> bookEntities = new ArrayList<>();
        for (BookModel bookModel : bookModels) {
            BookEntity bookEntity = new BookEntity();
            BeanUtils.copyProperties(bookModel, bookEntity);
            bookEntities.add(bookEntity);
        }
        return bookEntities;
    }

    public static List<BookModel> booksToModels(List<BookEntity> bookEntities) {
        List<BookModel> bookModels = new ArrayList<>();
        for (BookEntity bookEntity : bookEntities) {
            BookModel bookModel = new BookModel();
            BeanUtils.copyProperties(bookEntity, bookModel);
            bookModels.add(bookModel);
        }
        return bookModels;
    }


    public static CustomerModel customerToModel(CustomerEntity customerEntity) {
        if (customerEntity == null) {
            return null;
        }
        CustomerModel customerModel = new CustomerModel();
        BeanUtils.copyProperties(customerEntity, customerModel);

        return customerModel;
    }


    public static CustomerEntity customerToEntity(CustomerModel customerModel) {
        if (customerModel == null) {
            return null;
        }
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerModel, customerEntity);
        return customerEntity;
    }


    public static List<CustomerEntity> customersToEntities(List<CustomerModel> customerModels) {
        List<CustomerEntity> customerEntities = new ArrayList<>();
        for (CustomerModel customerModel : customerModels) {
            CustomerEntity customerEntity = new CustomerEntity();
            BeanUtils.copyProperties(customerModel, customerEntity);
            customerEntities.add(customerEntity);
        }
        return customerEntities;
    }

    public static List<CustomerModel> customersToModels(List<CustomerEntity> customerEntities) {
        List<CustomerModel> customerModels = new ArrayList<>();
        for (CustomerEntity customerEntity : customerEntities) {
            CustomerModel customerModel = new CustomerModel();
            BeanUtils.copyProperties(customerEntity, customerModel);
            customerModels.add(customerModel);
        }
        return customerModels;
    }

    public static OrderModel orderToModel(OrderEntity orderEntity) {
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderEntity, orderModel);

        CustomerModel customerModel = CustomBeanUtils.customerToModel(orderEntity.getCustomer());
        List<BookModel> bookModels = CustomBeanUtils.booksToModels(orderEntity.getBooks());

        orderModel.setCustomer(customerModel);
        orderModel.setBooks(bookModels);

        return orderModel;
    }
}
