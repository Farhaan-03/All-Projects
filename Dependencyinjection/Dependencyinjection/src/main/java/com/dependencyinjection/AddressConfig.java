package com.dependencyinjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class AddressConfig {

    @Bean("add2")
    public Address getAddress(){
        return new Address("asv","hyd",6005);
    }

    @Bean("add3")
    public Address getAddress2(){
        return new Address("453","hyd",8009);
    }



}
