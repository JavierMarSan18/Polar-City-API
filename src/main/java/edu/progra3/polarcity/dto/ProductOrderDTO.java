package edu.progra3.polarcity.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ProductOrderDTO {
    private Long id;
    @NotNull(message = "El producto no puede ser nulo.")
    private ProductDTO product;
    @NotNull(message = "La cantidad del producto no puede ser nula.")
    private Integer quantity;
    private Double amount;
}
