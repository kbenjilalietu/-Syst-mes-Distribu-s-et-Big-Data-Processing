package com.example.billingservice;

import com.example.billingservice.dto.InvoiceRequestDTO;
import com.example.billingservice.services.InvoiceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients  //pour activer le service openFeign
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner strat(InvoiceService invoiceService)
    {
        return args -> {
            invoiceService.addInvoice(new InvoiceRequestDTO(BigDecimal.valueOf(90000), "7c76a057-a56b-4f61-95a1-c98c43cfb5dc"));
            invoiceService.addInvoice(new InvoiceRequestDTO(BigDecimal.valueOf(40000), "7c76a057-a56b-4f61-95a1-c98c43cfb5dc"));
            invoiceService.addInvoice(new InvoiceRequestDTO(BigDecimal.valueOf(10000), "7c76a057-a56b-4f61-95a1-c98c43cfb5dc"));
        };
    }

}
