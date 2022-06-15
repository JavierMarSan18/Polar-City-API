package edu.progra3.polarcity.entities;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "products_of_order")
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private Product product;
    private Integer quantity;
    private Double amount;
}
