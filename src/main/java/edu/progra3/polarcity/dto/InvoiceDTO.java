package edu.progra3.polarcity.dto;

import edu.progra3.polarcity.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class InvoiceDTO {
    private Long id;
    private Double amount;
    private Date date;
    private Client client;
}
