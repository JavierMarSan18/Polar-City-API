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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{

    public static final String ORDER_NOT_FOUND = "Orden no encontrada.";
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Encontrar todas las ordenes en la base de datos
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

    //Metodo para encontrar una orden por el id
    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND));
        return mapDTO(order);
    }

    //Metodo para encontrar una orden por el nombre del cliente
    @Override
    public List<OrderDTO> findAllByClient(String clientName) {
        List<Order> orders = orderRepository.findAllByClient(clientName);
        return orders.stream().map(this::mapDTO).collect(Collectors.toList());
    }


    //Se genera una nueva orden con estado inicial "Pendiente"
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

    //Metodo para actualizar una orden
    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order currentOrder = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND));

        List<ProductOrder> productOrders = orderDTO.getProducts().stream().map(this::MapEntity).collect(Collectors.toList());
        List<ProductOrder> updatedProductOrders = new ArrayList<>();

        //Se obtienen las referencias de los productos para los productos de las ordenes y se actualizan.
        AtomicReference<Long> productId = new AtomicReference<>(0L);
        productOrders.forEach(productOrder -> {
            productId.set(productOrder.getProduct().getId());
            if(!productRepository.existsById(productId.get())){
                throw  new NotFoundException("EL producto con id: " + productId.get() + " no existe");
            }
            productOrder.setProduct(productRepository.getReferenceById(productId.get()));
            productOrder.setAmount(calculateAmount(productId.get(), productOrder.getQuantity()));
            updatedProductOrders.add(productOrderRepository.save(productOrder));
        });

        //Se agregan los nuevos valores a la orden
        currentOrder.setId(id);
        currentOrder.setClient(orderDTO.getClient());
        currentOrder.setDate(Timestamp.from(Instant.now()));
        currentOrder.setTotal(calculateTotal(productOrders));

        //Se obtienen las referencias de los productos para las ordenes
        currentOrder.getProducts().clear();
        updatedProductOrders.forEach(updatedProductOrder -> {
            currentOrder.getProducts().add(productOrderRepository.getReferenceById(updatedProductOrder.getId()));
        });

        return mapDTO(currentOrder);
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
