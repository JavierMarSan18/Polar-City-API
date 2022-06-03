package edu.progra3.polarcity.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity @Table(name = "invoices")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Temporal(TemporalType.DATE)
    private Date date;

    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Client client;
}
