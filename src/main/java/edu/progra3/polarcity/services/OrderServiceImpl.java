package edu.progra3.polarcity.services;

import edu.progra3.polarcity.dto.OrderDTO;
import edu.progra3.polarcity.dto.ProductOrderDTO;
import edu.progra3.polarcity.entities.Order;
import edu.progra3.polarcity.entities.Product;
import edu.progra3.polarcity.entities.ProductOrder;
import edu.progra3.polarcity.entities.Stock;
import edu.progra3.polarcity.exceptions.ConflictException;
import edu.progra3.polarcity.exceptions.NotFoundException;
import edu.progra3.polarcity.repositories.OrderRepository;
import edu.progra3.polarcity.repositories.ProductOrderRepository;
import edu.progra3.polarcity.repositories.ProductRepository;
import edu.progra3.polarcity.repositories.StockRepository;
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
    public static final String DEFAULT_STATUS_ORDER = "Pendiente";
    public static final String DISPATCH_STATUS_ORDER = "Atendida";
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ModelMapper modelMapper;

    //Encontrar todas las ordenes en la base de datos
    @Override
    public Queue<OrderDTO> findAll() {
        LinkedList<Order> orders = orderRepository.findAll();
        LinkedList<Date> dates = new LinkedList<>();
        Queue<Order> enqueuedOrders = new LinkedList<>();

        //Ser ordenan las fechas de más antiguas a más recientes
        orders.forEach(order -> dates.add(order.getCreateAt()));
        dates.sort(new SortByDate());

        //Se encolan las ordenes por fecha
        dates.forEach(date ->{
            orders.forEach(order -> {
                if(date.getTime() == order.getCreateAt().getTime()){
                    enqueuedOrders.add(order);
                }
            });
        });

        return enqueuedOrders.stream().map(this::mapDTO).collect(Collectors.toCollection(ArrayDeque::new));
    }

    //Se buscan todas las ordenes por su estado
    @Override
    public Queue<OrderDTO> findAllByStatus(String status) {
        LinkedList<Order> orders = orderRepository.findAllByStatus(status);
        LinkedList<Date> dates = new LinkedList<>();
        Queue<Order> enqueuedOrders = new LinkedList<>();

        //Ser ordenan las fechas de más antiguas a más recientes
        orders.forEach(order -> dates.add(order.getCreateAt()));
        dates.sort(new SortByDate());

        //Se encolan las ordenes por fecha
        dates.forEach(date ->{
            orders.forEach(order -> {
                if(date.getTime() == order.getCreateAt().getTime()){
                    enqueuedOrders.add(order);
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
    public OrderDTO generateOrder(OrderDTO orderDTO) {
        Order order = new Order();
        List<ProductOrder> productOrders = orderDTO.getProducts().stream().map(this::MapEntity).collect(Collectors.toList());

        List<ProductOrder> createdProductOrders = new ArrayList<>();

        //Se obtienen las referencias de los productos para los productos de las ordenes y se guardan.
        AtomicReference<Long> productId = new AtomicReference<>(0L);
        AtomicReference<Stock> stock = new AtomicReference<>(null);
        AtomicReference<Product> product = new AtomicReference<>(null);
        productOrders.forEach(productOrder -> {
            productId.set(productOrder.getProduct().getId());
            product.set(productRepository.findById(productId.get()).orElseThrow(() -> new NotFoundException("Producto con id " + productId + " no encontrado")));
            stock.set(stockRepository.findByProduct(product.get()).orElseThrow(() -> new NotFoundException("Stock de producto no encontrado")));

            //Verifica que hay suficientes existencias en el stock
            if(stock.get().getQuantity() < productOrder.getQuantity()){
                throw new ConflictException("No hay suficientes existencias en el stock del producto con id: " + product.get().getId());
            }

            productOrder.setProduct(productRepository.getReferenceById(productId.get()));
            productOrder.setAmount(calculateAmount(productId.get(), productOrder.getQuantity()));
            createdProductOrders.add(productOrderRepository.save(productOrder));
        });

        //Se agregan los valores para generar la orden
        order.setClient(orderDTO.getClient());
        order.setStatus(DEFAULT_STATUS_ORDER);
        order.setCreateAt(Timestamp.from(Instant.now()));
        order.setTotal(calculateTotal(productOrders));

        //Se obtienen las referencias de los productos para las órdenes
        createdProductOrders.forEach(createdProductOrder -> {
            order.getProducts().add(productOrderRepository.getReferenceById(createdProductOrder.getId()));
        });

        return mapDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO dispatchOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ORDER_NOT_FOUND));

        if (!DEFAULT_STATUS_ORDER.equals(order.getStatus())){
            throw new ConflictException("La orden ya fue atendida.");
        }

        AtomicReference<Stock> stock = new AtomicReference<>(null);
        order.getProducts().forEach(productOrder -> {
            stock.set(stockRepository.findByProduct(productOrder.getProduct()).orElseThrow(() -> new NotFoundException("Stock de producto no encontrado.")));
            stock.get().setQuantity(calculateStock(stock.get().getQuantity(), productOrder.getQuantity()));
            stockRepository.save(stock.get());
        });

        order.setStatus(DISPATCH_STATUS_ORDER);
        order.setDispatchAt(Timestamp.from(Instant.now()));

        return mapDTO(orderRepository.save(order));
    }

    @Override
    public String deleteOrderById(Long id) {
        if(!orderRepository.existsById(id)){
            throw new NotFoundException(ORDER_NOT_FOUND);
        }
        orderRepository.deleteById(id);
        return "Orden eliminada exitosamente.";
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

    //Se hace la resta del stock
    private Integer calculateStock(Integer inStock, Integer quantity) {
        return inStock - quantity;
    }
}
