package edu.progra3.polarcity.controllers;

import edu.progra3.polarcity.dto.InvoiceDTO;
import edu.progra3.polarcity.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public List<InvoiceDTO> findAll(){
        return invoiceService.findAll();
    }

    @GetMapping("/clientName")
    public List<InvoiceDTO> findAllByClientName(@RequestParam("search") String name){
        return invoiceService.findAllByClientName(name);
    }

    @GetMapping("/date")
    public List<InvoiceDTO> findAllByDate(@RequestParam("search") String date){
        return invoiceService.findAllByDate(date);
    }
}
