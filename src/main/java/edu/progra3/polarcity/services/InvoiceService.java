package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.InvoiceDTO;

import java.util.Date;
import java.util.List;

public interface InvoiceService {
    List<InvoiceDTO> findAll();
    List<InvoiceDTO> findAllByClientName(String name);
    List<InvoiceDTO> findAllByDate(String date);
}
