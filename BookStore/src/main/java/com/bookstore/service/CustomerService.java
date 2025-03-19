package com.bookstore.service;

import com.bookstore.model.CustomerModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CustomerService {

    CustomerModel createCustomer(CustomerModel customerModel);

    List<CustomerModel> getAllCustomers();

    CustomerModel findCustomerById(Long id);

//    CustomerModel findCustomerByName(String name);

    boolean deleteCustomerById(Long id);

    CustomerModel updateCustomerById(Long id, CustomerModel customerModel);

    CustomerModel partiallyUpdateCustomer(Long id, Map<String, Object> updates);
}
