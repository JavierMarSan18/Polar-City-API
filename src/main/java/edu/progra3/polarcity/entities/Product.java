package edu.progra3.polarcity.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter @Setter
@Entity @Table(name = "products")
@AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
}
