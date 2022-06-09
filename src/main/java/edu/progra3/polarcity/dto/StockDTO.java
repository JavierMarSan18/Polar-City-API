package edu.progra3.polarcity.dto;

import edu.progra3.polarcity.entities.Product;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class StockDTO {
    private Long id;
    private Product product;
    private Integer quantity;
}
