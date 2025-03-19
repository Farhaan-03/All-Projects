package com.bookstore.controller;

import com.bookstore.error.ErrorApi;
import com.bookstore.model.OrderModel;
import com.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
//@RequestMapping("api/v1")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderModel> createOrder(@RequestBody OrderModel orderModel, Authentication authentication) {
        String username = authentication.getName();

        OrderModel createdOrder = orderService.createOrder(orderModel, username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("info", "Order created successfully")
                .body(createdOrder);
    }

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(Authentication authentication) {

        String username = authentication.getName();

        List<OrderModel> orders = orderService.getAllCustomerOrders(username);
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "All orders fetched successfully")
                .body(orders);
    }


    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        OrderModel order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Order fetched successfully")
                    .body(order);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Order not found with ID: " + orderId, LocalDateTime.now()));
        }
    }


    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<?> deleteOrderById(@PathVariable Long orderId) {
        boolean isDeleted = orderService.deleteOrder(orderId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Order not found with ID: " + orderId, LocalDateTime.now()));
        }
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<?> updateOrderById(@PathVariable Long orderId, @RequestBody OrderModel orderModel) {
        OrderModel updatedOrder = orderService.updateOrder(orderId, orderModel);
        if (updatedOrder != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Order updated successfully")
                    .body(updatedOrder);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Order not found with ID: " + orderId, LocalDateTime.now()));
        }
    }


    @GetMapping("/orders/customers")
    public ResponseEntity<?> getAllOrders() {
        List<OrderModel> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Customer orders fetched successfully")
                .body(orders);
    }

    @GetMapping("/orders/customers/name/{name}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable String name) {
        List<OrderModel> orders = orderService.getOrdersByCustomerName(name);

        if (!orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Customer orders fetched successfully")
                    .body(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "No orders found for customer with name: " + name, LocalDateTime.now()));
        }
    }


}
