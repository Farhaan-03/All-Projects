package com.bookstore.service;

import com.bookstore.model.OrderModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderModel createOrder(OrderModel orderModel, String username);

    List<OrderModel> getAllOrders();

    OrderModel getOrderById(Long orderId);

    boolean deleteOrder(Long orderId);

    OrderModel updateOrder(Long orderId, OrderModel orderModel);

    List<OrderModel> getOrdersByCustomerId(Long customerId);

    List<OrderModel> getOrdersByCustomerName(String name);

    List<OrderModel> getAllCustomerOrders(String username);
}
