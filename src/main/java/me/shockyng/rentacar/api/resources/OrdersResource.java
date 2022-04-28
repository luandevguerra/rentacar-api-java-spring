package me.shockyng.rentacar.api.resources;

import me.shockyng.rentacar.api.records.OrderDTO;
import me.shockyng.rentacar.api.service.OrdersService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/orders")
public class OrdersResource {

    private final OrdersService service;

    public OrdersResource(OrdersService service) {
        this.service = service;
    }

    @GetMapping
    public ArrayList<OrderDTO> getOrder() {
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable("id") Long id) {
        return service.getOrder(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return service.createOrder(orderDTO);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@PathVariable("id") Long id, @RequestBody OrderDTO orderDTO) {
        return service.updateOrder(id, orderDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable("id") Long id) {
        service.deleteOrder(id);
    }


}
