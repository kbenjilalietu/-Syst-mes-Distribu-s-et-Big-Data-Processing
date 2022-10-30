package com.example.customerservice.web;

import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.dto.CustomerResponseDTO;
import com.example.customerservice.services.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CustomerRestAPI
{
    private CustomerService customerService;

    public CustomerRestAPI(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(path = "/customers")
    public List<CustomerResponseDTO> allCustomers(){
        return  customerService.listCustomers();
    }

    @PostMapping(path = "/customers")
    public CustomerResponseDTO save(@RequestBody CustomerRequestDTO customerRequestDTO)
    {
        return customerService.addCustomer(customerRequestDTO);
    }

    @GetMapping(path = "/customers/{id}")
    public CustomerResponseDTO getCustomer(@PathVariable String id)
    {
        return customerService.getCustomer(id);
    }

    @PutMapping(path = "/customers/{id}")
    public CustomerResponseDTO update(@PathVariable String id, @RequestBody CustomerRequestDTO customerRequestDTO)
    {
        return customerService.update(id, customerRequestDTO);
    }

}
