package edu.progra3.polarcity.dto;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
    @NotNull (message = "El ID no puede ser nulo.")
    private Long id;
    @NotEmpty (message = "El nombre no puede estar vacío o ser nulo.")
    private String name;
    @NotNull (message = "El precio no puede ser nulo.")
    private Double price;
}
