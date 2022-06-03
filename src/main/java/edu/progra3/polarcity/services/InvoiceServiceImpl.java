package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.InvoiceDTO;
import edu.progra3.polarcity.entities.Client;
import edu.progra3.polarcity.entities.Invoice;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.ClientRepository;
import edu.progra3.polarcity.repositories.InvoiceRepository;
import edu.progra3.polarcity.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MapUtil mapUtil;

    @Override
    public List<InvoiceDTO> findAll() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(invoice -> mapUtil.mapDTO(invoice)).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findAllByClientName(String name) {
        Client client = clientRepository.findByName(name).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        List<Invoice> invoices = invoiceRepository.findAllByClient(client);
        return invoices.stream().map(invoice -> mapUtil.mapDTO(invoice)).collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> findAllByDate(String date) {
        try
        {
            //Se convierte el string en date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date formattedDate = sdf.parse(date);

            //Se buscan las facturas por medio de la fecha
            List<Invoice> invoices = invoiceRepository.findAllByDate((formattedDate));
            return invoices.stream().map(invoice -> mapUtil.mapDTO(invoice)).collect(Collectors.toList());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
