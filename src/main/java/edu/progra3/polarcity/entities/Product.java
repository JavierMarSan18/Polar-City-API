package edu.progra3.polarcity.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "products")

@Getter @Setter

public class Product {
    @Id
    private Long id;
    private String name;
    private Double price;
    private Integer stock;

}
