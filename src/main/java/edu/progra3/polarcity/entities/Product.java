package edu.progra3.polarcity.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity @Table(name = "products")
@AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;

//    @OneToOne(mappedBy = "product")
//    private ProductOrder productOrder;
}
