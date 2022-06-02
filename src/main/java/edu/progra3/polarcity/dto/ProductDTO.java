package edu.progra3.polarcity.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ProductDTO {
    @NotNull (message = "El ID no puede estar vacio")
    private Long id;
    @NotBlank (message = "El Nombre no puede estar vacio")
    private String name;

    @NotNull (message = "El precio no puede estar vacio")
    private Double price;
    @NotNull (message = "El stock no puede estar vacio")
    private Integer stock;
}
