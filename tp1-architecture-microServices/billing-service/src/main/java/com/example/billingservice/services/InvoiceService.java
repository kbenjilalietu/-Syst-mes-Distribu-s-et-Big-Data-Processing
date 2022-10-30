package com.example.billingservice.services;

import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.dto.InvoiceResponseDTO;

import java.util.List;

public interface InvoiceService
{
    InvoiceResponseDTO addInvoice(InvoiceRequestDTO invoiceRequestDTO);
    InvoiceResponseDTO getInvoice(String invoiceId);
    List<InvoiceResponseDTO> invoicesByCustomers(String customerId);

    List<InvoiceResponseDTO> getAllInvoices();
}
