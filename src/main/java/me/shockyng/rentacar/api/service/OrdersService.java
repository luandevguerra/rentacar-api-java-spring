package me.shockyng.rentacar.api.service;

import me.shockyng.rentacar.api.exceptions.OrderNotFoundException;
import me.shockyng.rentacar.api.models.Order;
import me.shockyng.rentacar.api.records.OrderDTO;
import me.shockyng.rentacar.api.repository.OrdersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final ModelMapper mapper = new ModelMapper();

    private final OrdersRepository repository;

    public OrdersService(OrdersRepository repository) {
        this.repository = repository;
    }

    public ArrayList<OrderDTO> getOrders() {
        List<Order> orders = repository.findAll();
        if (orders.isEmpty()) throw new OrderNotFoundException();
        else return (ArrayList<OrderDTO>) orders.stream().map(o -> mapper.map(o, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrder(Long id) {
        return mapper.map(repository.findById(id).get(), OrderDTO.class);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        return mapper.map(repository.save(mapper.map(orderDTO, Order.class)), OrderDTO.class);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = mapper.map(orderDTO, Order.class);
        order.setId(getOrder(id).getId());
        return mapper.map(repository.save(order), OrderDTO.class);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
