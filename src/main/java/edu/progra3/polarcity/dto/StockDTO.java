package edu.progra3.polarcity.dto;

import edu.progra3.polarcity.entities.Product;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class StockDTO {
    private Long id;
    @NotNull(message = "El producto no puede ser nulo.")
    private Product product;
    @NotNull(message = "La cantidad del producto no puede ser nula.")
    private Integer quantity;
}
