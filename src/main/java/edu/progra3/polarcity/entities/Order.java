package edu.progra3.polarcity.entities;

import com.sun.tools.javac.util.List;
import edu.progra3.polarcity.dto.ProductOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean soldOut;
    private List<Product> products;

    @JoinColumn(name = "client_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Client client;
}
