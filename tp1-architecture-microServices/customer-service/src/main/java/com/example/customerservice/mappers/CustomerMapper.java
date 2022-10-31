package com.example.customerservice.mappers;

import com.example.customerservice.dto.CustomerRequestDTO;
import com.example.customerservice.dto.CustomerResponseDTO;
import com.example.customerservice.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//Mapper DTO <==> Entities
@Mapper(componentModel = "spring")
public interface CustomerMapper
{
    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);
    Customer customerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);
}
