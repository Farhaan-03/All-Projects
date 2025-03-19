package com.bookstore.controller;

import com.bookstore.error.ErrorApi;
import com.bookstore.model.CustomerModel;
import com.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("api/v1")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerModel customerModel) {
        CustomerModel createdCustomer = customerService.createCustomer(customerModel);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("info", "Customer created successfully")
                .body(createdCustomer);
    }


    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        List<CustomerModel> customers = customerService.getAllCustomers();

        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Customers fetched successfully")
                .body(customers);
    }


    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        CustomerModel customer = customerService.findCustomerById(id);

        if (customer != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Customer fetched successfully")
                    .body(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Customer Not Found with given Id " + id, LocalDateTime.now()));
        }
    }

//    @GetMapping("/customers/name/{name}")
//    public ResponseEntity<?> getCustomerByName(@PathVariable String name) {
//        CustomerModel customer = customerService.findCustomerByName(name);
//
//        if (customer != null) {
//            return ResponseEntity.status(HttpStatus.OK)
//                    .header("info", "Customer fetched successfully")
//                    .body(customer);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ErrorApi(404, "Customer Not Found with the name: " + name, LocalDateTime.now()));
//        }
//    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id) {
        boolean isDeleted = customerService.deleteCustomerById(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .header("info", "Customer deleted successfully")
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Customer Not Found with given Id " + id, LocalDateTime.now()));
        }
    }

    @PreAuthorize("hasAuthority('customer')")
    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomerById(@PathVariable Long id, @RequestBody CustomerModel customerModel) {
        CustomerModel updatedCustomer = customerService.updateCustomerById(id, customerModel);

        if (updatedCustomer != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Customer updated successfully")
                    .body(updatedCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Customer Not Found with given Id " + id, LocalDateTime.now()));
        }
    }

    //    @PreAuthorize("hasAuthority('customer')")
    @PatchMapping("/customers/{id}")
    public ResponseEntity<?> partiallyUpdateCustomer(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        CustomerModel updatedCustomer = customerService.partiallyUpdateCustomer(id, updates);

        if (updatedCustomer != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("info", "Customer partially updated successfully")
                    .body(updatedCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorApi(404, "Customer Not Found with given Id " + id, LocalDateTime.now()));
        }
    }


}
