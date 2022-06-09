package edu.progra3.polarcity.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String status ;
    private Set<ProductOrderDTO> products;
    private Date date;
    private Double total;
}
