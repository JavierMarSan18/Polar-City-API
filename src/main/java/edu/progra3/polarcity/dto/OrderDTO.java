package edu.progra3.polarcity.dto;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter @Setter
@EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String status ;
    private String client;
    private List<ProductOrderDTO> products;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Double total;
}
