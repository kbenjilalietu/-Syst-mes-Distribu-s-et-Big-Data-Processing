package com.example.customerservice.services;

import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.dto.CustomerResponseDTO;
import com.example.customerservice.entities.Customer;
import com.example.customerservice.mappers.CustomerMapper;
import com.example.customerservice.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService
{
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerResponseDTO addCustomer(CustomerRequestDTO customerRequestDTO)
    {
        Customer customer = customerMapper.customerRequestDTOToCustomer(customerRequestDTO);
        customer.setId(UUID.randomUUID().toString());
        Customer saveCustomer = customerRepository.save(customer);
        CustomerResponseDTO customerResponseDTO = customerMapper.customerToCustomerResponseDTO(saveCustomer);
        return customerResponseDTO;
    }

    @Override
    public CustomerResponseDTO getCustomer(String id) {
        Customer customer = customerRepository.findById(id).get();
        return customerMapper.customerToCustomerResponseDTO(customer);
    }

    @Override
    public CustomerResponseDTO update(String id, CustomerRequestDTO customerRequestDTO)
    {
        CustomerResponseDTO findCustomer = getCustomer(id);
        Customer customer = customerMapper.customerRequestDTOToCustomer(customerRequestDTO);
        customer.setId(findCustomer.getId());
        Customer updatedCustomer = customerRepository.save(customer);
        CustomerResponseDTO customerResponseDTO = customerMapper.customerToCustomerResponseDTO(updatedCustomer);
        return customerResponseDTO;
    }

    @Override
    public List<CustomerResponseDTO> listCustomers()
    {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseDTO> customerResponseDTOS
                = customers.stream()
                     .map(cust -> customerMapper.customerToCustomerResponseDTO(cust))
                     .collect(Collectors.toList());
        return customerResponseDTOS;
    }
}
