package edu.progra3.polarcity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private Integer stock;
}
