package com.example.billingservice.web;

import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.dto.InvoiceResponseDTO;
import com.example.billingservice.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class InvoiceRestController
{
    private InvoiceService invoiceService;

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping(path = "/invoices/{id}")
    public InvoiceResponseDTO getInvoice(@PathVariable String id)
    {
        return invoiceService.getInvoice(id);
    }

    @GetMapping(path = "/invoicesByCustomer/{customerId}")
    public List<InvoiceResponseDTO> getInvoicesByCustomer(@PathVariable String customerId)
    {
        return invoiceService.invoicesByCustomers(customerId);
    }

    @GetMapping(path = "/invoices")
    public List<InvoiceResponseDTO> allInvoices()
    {
        return invoiceService.getAllInvoices();
    }

    @PostMapping(path = "/invoices")
    public InvoiceResponseDTO addInvoice(@RequestBody InvoiceRequestDTO invoiceRequestDTO)
    {
        return  invoiceService.addInvoice(invoiceRequestDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
