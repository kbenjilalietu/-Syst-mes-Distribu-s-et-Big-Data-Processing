package com.example.billingservice.services;

import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.dto.InvoiceResponseDTO;
import com.example.billingservice.entities.Customer;
import com.example.billingservice.entities.Invoice;
import com.example.billingservice.exceptions.CustomerNotFoundException;
import com.example.billingservice.mappers.InvoiceMapper;
import com.example.billingservice.openfeign.CustomerRestClient;
import com.example.billingservice.repositories.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService
{
    private InvoiceRepository invoiceRepository;
    private InvoiceMapper invoiceMapper;

    private CustomerRestClient customerRestClient;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, CustomerRestClient customerRestClient)
    {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRestClient = customerRestClient;
    }

    @Override
    public InvoiceResponseDTO addInvoice(InvoiceRequestDTO invoiceRequestDTO)
    {
        /*
          Vérification de l'intégrité référencielle Invoice / Customer
         */
        Customer customer = null;
        try {
             customer = customerRestClient.getCustomer(invoiceRequestDTO.getCustomerId());
        }
        catch (Exception e)
        {
            throw new CustomerNotFoundException("Customer not found");
        }
        Invoice invoice = invoiceMapper.fromInvoiceRequestDTO(invoiceRequestDTO);
        invoice.setId(UUID.randomUUID().toString());
        invoice.setDate(new Date());
        Invoice saveInvoice = invoiceRepository.save(invoice); // save est de jpa
        saveInvoice.setCustomer(customer);
        InvoiceResponseDTO invoiceResponseDTO = invoiceMapper.fromInvoice(saveInvoice);
        return invoiceResponseDTO;
    }

    @Override
    public InvoiceResponseDTO getInvoice(String invoiceId)
    {
        Invoice invoice = invoiceRepository.findById(invoiceId).get();
        // get customer from customer microservice by openFeign
        Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
        invoice.setCustomer(customer);
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
    public List<InvoiceResponseDTO> invoicesByCustomers(String customerId)
    {
        List<Invoice> invoices = invoiceRepository.findByCustomerId(customerId);
        for(Invoice invoice:invoices)
        {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                        .map(invoice -> invoiceMapper.fromInvoice(invoice))
                        .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceResponseDTO> getAllInvoices()
    {
        List<Invoice> invoices = invoiceRepository.findAll();
        for(Invoice invoice:invoices)
        {
            Customer customer = customerRestClient.getCustomer(invoice.getCustomerId());
            invoice.setCustomer(customer);
        }
        return invoices.stream()
                .map(invoice -> invoiceMapper.fromInvoice(invoice))
                .collect(Collectors.toList());
    }
}
