package com.example.customerservice;

import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerService customerService){
        return args -> {
            customerService.addCustomer(new CustomerRequestDTO("Adria", "adria@adria.com"));
            customerService.addCustomer(new CustomerRequestDTO("OpenLab", "open@openLab.com"));
            customerService.addCustomer(new CustomerRequestDTO("Nimbleways", "nimbleways@nimbleways.com"));
        };
    }


}
