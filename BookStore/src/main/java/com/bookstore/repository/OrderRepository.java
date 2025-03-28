package com.bookstore.repository;

import com.bookstore.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByCustomerCustomerId(Long customerId);

    List<OrderEntity> findByCustomerName(String name);

}
