package com.bookstore.service;

import com.bookstore.entity.CustomerEntity;
import com.bookstore.model.CustomerModel;
import com.bookstore.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomerServiceImplementation implements CustomerService, UserDetailsService {

    @Autowired
    CustomerRepository customerRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByName(username);
    }

    @Override
    public CustomerModel createCustomer(CustomerModel customerModel) {

        CustomerEntity customerEntity = CustomerEntity.builder()
                .name(customerModel.getName())
                .email(customerModel.getEmail())
                .phoneNo(customerModel.getPhoneNo())
                .password(new BCryptPasswordEncoder().encode(customerModel.getPassword()))
                .authorities("customer")
                .build();

        customerEntity = customerRepository.save(customerEntity);
        return CustomBeanUtils.customerToModel(customerEntity);
    }


    @Override
    public List<CustomerModel> getAllCustomers() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        return CustomBeanUtils.customersToModels(customerEntities);
    }

    @Override
    public CustomerModel findCustomerById(Long id) {
        Optional<CustomerEntity> customerEntity = customerRepository.findById(id);
        return customerEntity.map(CustomBeanUtils::customerToModel).orElse(null);
    }

//    @Override
//    public CustomerModel findCustomerByName(String name) {
//        Optional<CustomerEntity> customerEntity = customerRepository.findByName(name);
//        return customerEntity.map(CustomBeanUtils::customerToModel).orElse(null);
//    }

    @Override
    public boolean deleteCustomerById(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CustomerModel updateCustomerById(Long id, CustomerModel customerModel) {
        Optional<CustomerEntity> optional = customerRepository.findById(id);
        if (optional.isPresent()) {
            CustomerEntity customerEntity = optional.get();

            customerEntity.setName(customerModel.getName());
            customerEntity.setEmail(customerModel.getEmail());
            customerEntity.setPhoneNo(customerModel.getPhoneNo());
            customerEntity.setPassword(new BCryptPasswordEncoder().encode(customerModel.getPassword()));

            customerEntity = customerRepository.save(customerEntity);

            return CustomBeanUtils.customerToModel(customerEntity);
        }
        return null;
    }


    @Override
    public CustomerModel partiallyUpdateCustomer(Long id, Map<String, Object> updates) {
        Optional<CustomerEntity> existingCustomer = customerRepository.findById(id);

        if (existingCustomer.isPresent()) {
            CustomerEntity customerEntity = existingCustomer.get();

            if (updates.containsKey("name")) {
                customerEntity.setName((String) updates.get("name"));
            }
            if (updates.containsKey("email")) {
                customerEntity.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("phoneNo")) {
                customerEntity.setPhoneNo((Long) updates.get("phoneNo"));
            }
            if (updates.containsKey("password")) {
                customerEntity.setPassword((String) updates.get("password"));
            }

            customerRepository.save(customerEntity);
            return CustomBeanUtils.customerToModel(customerEntity);
        }
        return null;
    }


}
