package edu.progra3.polarcity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity @Table(name = "orders")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;

//    @JsonBackReference
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name="productos_en_orden", joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "product_order_id", referencedColumnName = "id"))
//    private Set<ProductOrder> products = new HashSet<>();
    @Temporal(TemporalType.DATE)
    private Date date;
    private Double amount;
}