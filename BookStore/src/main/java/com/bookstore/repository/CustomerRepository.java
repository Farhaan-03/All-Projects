package com.bookstore.repository;

import com.bookstore.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

//    Optional<CustomerEntity> findByName(String name);

    UserDetails findByName(String name);


}
