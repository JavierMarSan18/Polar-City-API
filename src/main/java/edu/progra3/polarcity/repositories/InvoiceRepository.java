package edu.progra3.polarcity.repositories;

import edu.progra3.polarcity.entities.Client;
import edu.progra3.polarcity.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByClient(Client client);
    List<Invoice> findAllByDate(Date date);
}
