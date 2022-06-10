package edu.progra3.polarcity.dto;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String status ;
    @NotEmpty(message = "El nombre del cliente no puede estar vacío.")
    private String client;
    @NotEmpty(message = "La lista de productos no puede estar vacía.")
    private List<ProductOrderDTO> products;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dispatchAt;
    private Double total;
}
