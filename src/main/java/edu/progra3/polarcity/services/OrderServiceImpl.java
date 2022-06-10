package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.entities.Order;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.entities.ProductOrder;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.OrderRepository;
import edu.progra3.polarcity.repositories.ProductOrderRepository;
import edu.progra3.polarcity.repositories.ProductRepository;
import edu.progra3.polarcity.utils.SortByDate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Queue<OrderDTO> findAll() {
        LinkedList<Order> orders = orderRepository.findAll();
        LinkedList<Date> dates = new LinkedList<>();
        Queue<Order> enqueuedOrders = new LinkedList<>();

        //Ser ordenan las fechas de más antiguas a más recientes
        orders.forEach(order -> dates.add(order.getDate()));
        dates.sort(new SortByDate());

        //Se encolan las ordenes por fecha
        dates.forEach(date -> {
            orders.forEach(order -> {
                if(date.getTime() == order.getDate().getTime()){
                    enqueuedOrders.add(order);
                    orders.remove(order);
                }
            });
        });

        return enqueuedOrders.stream().map(this::mapDTO).collect(Collectors.toCollection(ArrayDeque::new));
    }

    @Override
    //MashMap<productId,quantity>
    public OrderDTO generateOrder(List<ProductOrderDTO> productOrderDTOS, String clientName) {
        Order order = new Order();
        List<ProductOrder> productOrders = productOrderDTOS.stream().map(this::MapEntity).collect(Collectors.toList());


        List<ProductOrder> createdProductOrders = new ArrayList<>();

        //Se obtienen las referencias de los productos para los productos de las ordenes y se guardan.
        AtomicReference<Long> productId = new AtomicReference<>(0L);
        productOrders.forEach(productOrder -> {
            productId.set(productOrder.getProduct().getId());
            if(!productRepository.existsById(productId.get())){
                throw  new NotFoundException("EL producto con id: " + productId.get() + " no existe");
            }
            productOrder.setProduct(productRepository.getReferenceById(productId.get()));
            productOrder.setAmount(calculateAmount(productId.get(), productOrder.getQuantity()));
            createdProductOrders.add(productOrderRepository.save(productOrder));
        });

        //Se agregan los valores para generar la orden
        order.setClient(clientName);
        order.setStatus("Pendiente");
        order.setDate(Timestamp.from(Instant.now()));
        order.setTotal(calculateTotal(productOrders));

        //Se obtienen las referencias de los productos para las ordenes
        createdProductOrders.forEach(createdProductOrder -> {
            order.getProducts().add(productOrderRepository.getReferenceById(createdProductOrder.getId()));
        });

        return mapDTO(orderRepository.save(order));
    }


    @Override
    public OrderDTO findById(Long id) {
        return null;
    }

    //Mapear la entidad order a orderDTO
    private OrderDTO mapDTO(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }

    //Mapear la entidad productOrder a productOrderDTO
    private ProductOrder MapEntity(ProductOrderDTO productOrderDTO) {
        return modelMapper.map(productOrderDTO, ProductOrder.class);
    }

    //Calcular el monto del producto en la orden
    private Double calculateAmount(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return product.getPrice() * quantity;
    }

    //Se calcula el total del monto de la orden
    private Double calculateTotal(List<ProductOrder> productOrders) {
        AtomicReference<Double> amount = new AtomicReference<>(0.0);
        productOrders.forEach(productOrder -> {
            amount.updateAndGet(v -> v + productOrder.getQuantity() * productOrder.getProduct().getPrice());
        });

        return amount.get();
    }
}
