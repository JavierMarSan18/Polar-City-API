package edu.progra3.polarcity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductOrderDTO {
    @NotNull(message = "El codigo del producto no puede ser nulo")
    private Long code;
    @NotNull(message = "La cantidad del producto no puede ser nulo")
    private Integer quantity;
}
