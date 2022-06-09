package edu.progra3.polarcity.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ProductOrderDTO {
    private Long id;
    private ProductDTO product;
    private Integer quantity;
    private Double amount;
}
