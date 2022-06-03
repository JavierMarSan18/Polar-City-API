package edu.progra3.polarcity.utils;

import edu.progra3.polarcity.dto.InvoiceDTO;
import edu.progra3.polarcity.dto.ProductDTO;
import edu.progra3.polarcity.dto.UserDTO;
import edu.progra3.polarcity.entities.Invoice;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MapUtil {

    @Autowired
    private ModelMapper modelMapper;

    public UserDTO mapDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }

    public ProductDTO mapDTO(Product product){
        return modelMapper.map(product, ProductDTO.class);
    }

    public InvoiceDTO mapDTO(Invoice invoice){
        return modelMapper.map(invoice, InvoiceDTO.class);
    }

    public User mapEntity(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public Product mapEntity(ProductDTO productDTO){
        return modelMapper.map(productDTO, Product.class);
    }

    public Invoice mapEntity(InvoiceDTO invoiceDTO){
        return modelMapper.map(invoiceDTO, Invoice.class);
    }
}
