package me.luandevguerra.rentacar.api.service;

import me.luandevguerra.rentacar.api.dtos.OrderDTO;
import me.luandevguerra.rentacar.api.exceptions.OrderNotFoundException;
import me.luandevguerra.rentacar.api.models.Order;
import me.luandevguerra.rentacar.api.repository.OrdersRepository;
import me.luandevguerra.rentacar.api.utils.DataTestProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdersServiceTest {

    private final ModelMapper mapper = new ModelMapper();

    @InjectMocks
    private OrdersService service;

    @Mock
    private OrdersRepository repository;

    @Test
    void shouldReturnAListOfOrdersWhenGetOrdersIsCalled() {
        ArrayList<Order> orderList = DataTestProvider.getOrderList();

        when(repository.findAll()).thenReturn(orderList);

        ArrayList<OrderDTO> orderListDto = service.getOrders();

        assertEquals(orderList.size(), orderListDto.size());
        assertEquals(orderList.get(0).getId(), orderListDto.get(0).getId());
        assertEquals(orderList.get(0).getDate(), orderListDto.get(0).getDate());
        assertEquals(orderList.get(0).getPrice(), orderListDto.get(0).getPrice());
    }

    @Test
    void shouldThrowOrderNotFoundExceptionOnceNoOrdersWereFoundWhenGetOrdersIsCalled() {
        List<Order> orderEmptyList = Collections.emptyList();

        when(repository.findAll()).thenReturn(orderEmptyList);

        assertThrows(OrderNotFoundException.class, () -> service.getOrders());
    }

    @Test
    void shouldReturnAnOrderWhenGetOrderIsCalled() {
        long id = 1L;
        Order order = DataTestProvider.getOrder(id);

        when(repository.findById(id)).thenReturn(Optional.of(order));

        OrderDTO orderDTO = service.getOrder(id);

        assertEquals(order.getId(), orderDTO.getId());
        assertEquals(order.getDate(), orderDTO.getDate());
        assertEquals(order.getPrice(), orderDTO.getPrice());
    }

    @Test
    void shouldThrowNoSuchElementExceptionOnceNoOrderWasFoundWhenGetOrderIsCalled() {
        long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getOrder(id));
    }

    @Test
    void shouldReturnAnOrderWhenCreateOrderIsCalled() {
        long id = 1L;
        Order order = DataTestProvider.getOrder(id);
        OrderDTO orderDTO = mapper.map(order, OrderDTO.class);

        when(repository.save(order)).thenReturn(order);

        OrderDTO orderDTO1 = service.createOrder(orderDTO);

        assertEquals(order.getId(), orderDTO1.getId());
        assertEquals(order.getDate(), orderDTO1.getDate());
        assertEquals(order.getPrice(), orderDTO1.getPrice());
    }

    @Test
    void shouldReturnAnOrderWhenUpdateOrderIsCalled() {
        long id = 1L;
        Order order = DataTestProvider.getOrder(id);
        OrderDTO orderDTO = mapper.map(order, OrderDTO.class);

        when(repository.findById(id)).thenReturn(Optional.of(order));
        when(repository.save(order)).thenReturn(order);

        OrderDTO orderDTO1 = service.updateOrder(id, orderDTO);

        assertEquals(order.getId(), orderDTO1.getId());
        assertEquals(order.getDate(), orderDTO1.getDate());
        assertEquals(order.getPrice(), orderDTO1.getPrice());
    }

    @Test
    void shouldThrowNoSuchElementExceptionOnceNoOrderWasFoundWhenUpdateOrderIsCalled() {
        long id = 1L;
        OrderDTO orderDTO = DataTestProvider.getOrderDTO(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateOrder(id, orderDTO));
    }

    @Test
    void shouldReturnThrowExceptionOnceNoOrderWasFound() {
        long id = 1L;

        doThrow(NoSuchElementException.class).when(repository).deleteById(id);

        assertThrows(NoSuchElementException.class, () -> service.deleteOrder(id));
    }

    @Test
    void shouldReturnNothingOnceDeleteByIdWasExecutedSuccessfully() {
        long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.deleteOrder(id);
    }
}