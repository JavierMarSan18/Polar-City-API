package edu.progra3.polarcity.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
    @NotNull (message = "El ID no puede estar vacío.")
    private Long id;
    @NotBlank (message = "El Nombre no puede estar vacío.")
    private String name;
    @NotNull (message = "El precio no puede estar vacío.")
    private Double price;
}
