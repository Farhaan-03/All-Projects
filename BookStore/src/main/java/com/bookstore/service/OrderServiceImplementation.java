package com.bookstore.service;

import com.bookstore.entity.BookEntity;
import com.bookstore.entity.CustomerEntity;
import com.bookstore.entity.OrderEntity;
import com.bookstore.model.BookModel;
import com.bookstore.model.OrderModel;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public OrderModel createOrder(OrderModel orderModel, String username) {

        CustomerEntity customerEntity = (CustomerEntity) customerRepository.findByName(username);
//        if (!customerOptional.isPresent()) {
//            throw new RuntimeException("Customer not found");
//        }
        List<Long> bookIds = orderModel.getBooks().stream().map(BookModel::getIsbn).toList();
        List<BookEntity> books = bookRepository.findAllById(bookIds);
//        if (books.isEmpty()) {
//            throw new RuntimeException("Books not found");
//        }

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setBooks(books);
        orderEntity.setCustomer(customerEntity);
        orderEntity.setQuantity(orderModel.getQuantity());

        orderEntity.getBooks().forEach(bookEntity -> {
            bookEntity.setStock(bookEntity.getStock() - orderModel.getQuantity());
            bookRepository.save(bookEntity);
        });

        int qty = orderEntity.getQuantity();
        double totalAmt = 0.0;
        for (double price : orderEntity.getBooks().stream().map(BookEntity::getPrice).toList()) {
            totalAmt += (qty * price);
        }
        orderEntity.setTotalAmount(totalAmt);

        orderEntity = orderRepository.save(orderEntity);
        return CustomBeanUtils.orderToModel(orderEntity);
    }


    @Override
    public List<OrderModel> getAllCustomerOrders(String username) {
        List<OrderEntity> orderEntities = orderRepository.findByCustomerName(username);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(orderEntities, new TypeToken<List<OrderModel>>() {
        }.getType());

    }

    @Transactional
    public List<OrderModel> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        return orders.stream()
                .map(CustomBeanUtils::orderToModel)
                .collect(Collectors.toList());
    }

    @Override
    public OrderModel getOrderById(Long orderId) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(CustomBeanUtils::orderToModel).orElse(null);
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return true;
        }
        return false;
    }


    public OrderModel updateOrder(Long orderId, OrderModel orderModel) {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            OrderEntity orderEntity = optionalOrder.get();

            orderEntity.setBooks(orderModel.getBooks().stream()
                    .map(bookModel -> bookRepository.findById(bookModel.getIsbn()).orElse(null))
                    .collect(Collectors.toList()));

            orderEntity.setCustomer(customerRepository.findById(orderModel.getCustomer().getCustomerId()).orElse(null));
            orderEntity.setQuantity(orderModel.getQuantity());

            double totalAmt = orderEntity.getBooks().stream()
                    .mapToDouble(book -> book.getPrice() * orderModel.getQuantity())
                    .sum();
            orderEntity.setTotalAmount(totalAmt);

            orderEntity = orderRepository.save(orderEntity);
            return CustomBeanUtils.orderToModel(orderEntity);
        }
        return null;
    }


    public List<OrderModel> getOrdersByCustomerId(Long customerId) {

        List<OrderEntity> orderEntities = orderRepository.findByCustomerCustomerId(customerId);

        if (orderEntities != null && !orderEntities.isEmpty()) {

//            return orderEntities.stream()
//                    .map(CustomBeanUtils::orderToModel)
//                    .collect(Collectors.toList());
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(orderEntities, new TypeToken<List<OrderModel>>() {
            }.getType());
        }
        return List.of();
    }

    public List<OrderModel> getOrdersByCustomerName(String name) {

        List<OrderEntity> orderEntities = orderRepository.findByCustomerName(name);

        if (orderEntities != null && !orderEntities.isEmpty()) {

            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(orderEntities, new TypeToken<List<OrderModel>>() {
            }.getType());
        }
        return List.of();
    }
}
