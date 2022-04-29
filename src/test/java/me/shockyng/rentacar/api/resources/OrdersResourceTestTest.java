package me.shockyng.rentacar.api.resources;

import me.shockyng.rentacar.api.dtos.OrderDTO;
import me.shockyng.rentacar.api.exceptions.CarNotFoundException;
import me.shockyng.rentacar.api.service.OrdersService;
import me.shockyng.rentacar.api.utils.AbstractResourceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static me.shockyng.rentacar.api.utils.DataTestProvider.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrdersResourceTestTest extends AbstractResourceTest {

    @Mock
    private OrdersService service;

    @InjectMocks
    private OrdersResource resource;

    @Override
    protected String getPath() {
        return "/v1/orders";
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
    }

    @Test
    void shouldReturnAListWithOrders() throws Exception {
        ArrayList<OrderDTO> orderList = getOrderList();

        when(service.getOrders()).thenReturn(orderList);

        mockMvc.perform(get(getPath()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(orderList.get(0).getId()))
                .andExpect(jsonPath("$[0].date").value(getDateAsStringArray(orderList.get(0).getDate())))
                .andExpect(jsonPath("$[0].price").value(orderList.get(0).getPrice()))
                .andExpect(jsonPath("$[1].id").value(orderList.get(1).getId()))
                .andExpect(jsonPath("$[1].date").value(getDateAsStringArray(orderList.get(1).getDate())))
                .andExpect(jsonPath("$[1].price").value(orderList.get(1).getPrice()))
                .andReturn();

        verify(service).getOrders();
        verify(service, times(1)).getOrders();
    }

    @Test
    void shouldReturn204CodeOnceNoCarsWereFoundWhenGetCarsWasCalled() throws Exception {
        when(service.getOrders()).thenThrow(CarNotFoundException.class);

        mockMvc.perform(get(getPath())).andExpect(status().isNoContent());

        verify(service).getOrders();
        verify(service, times(1)).getOrders();
    }

    @Test
    void shouldReturn200CodeAndACar() throws Exception {
        long pathParam = 1L;
        OrderDTO order = getOrder(pathParam);
        when(service.getOrder(anyLong())).thenReturn(order);

        mockMvc.perform(get(getPath() + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.date").value(getDateAsStringArray(order.getDate())))
                .andExpect(jsonPath("$.price").value(order.getPrice()));

        verify(service).getOrder(pathParam);
        verify(service, times(1)).getOrder(pathParam);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFound() throws Exception {
        long pathParam = 1L;
        when(service.getOrder(anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(getPath() + "/" + pathParam)).andExpect(status().isNoContent());

        verify(service).getOrder(pathParam);
        verify(service, times(1)).getOrder(pathParam);
    }


    @Test
    void shouldReturn201CodeAndACarOnResponseOnceACarWasCreated() throws Exception {
        OrderDTO order = getOrder(1L);

        when(service.createOrder(any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post(getPath()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.price").value(order.getPrice()))
                .andExpect(jsonPath("$.date").value(getDateAsStringArray(order.getDate())))
                .andReturn();

        verify(service).createOrder(order);
        verify(service, times(1)).createOrder(order);
    }

    @Test
    void shouldReturn201CodeAndACarOnResponseOnceACarWasUpdated() throws Exception {
        long pathParam = 1L;
        OrderDTO order = getOrder(pathParam);

        when(service.updateOrder(anyLong(), any(OrderDTO.class))).thenReturn(order);




        mockMvc.perform(put(getPath() + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect(jsonPath("$.price").value(order.getPrice()))
                .andExpect(jsonPath("$.date").value(getDateAsStringArray(order.getDate())))
                .andReturn();

        verify(service).updateOrder(pathParam, order);
        verify(service, times(1)).updateOrder(pathParam, order);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFoundWhenUpdateCarIsCalled() throws Exception {
        long pathParam = 1L;
        OrderDTO order = getOrder(pathParam);

        when(service.updateOrder(anyLong(), any(OrderDTO.class))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(getPath() + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(service).updateOrder(pathParam, order);
        verify(service, times(1)).updateOrder(pathParam, order);
    }

    @Test
    void shouldReturn200CodeOnceACarWasDeleted() throws Exception {
        long pathParam = 1L;

        doNothing().when(service).deleteOrder(anyLong());

        mockMvc.perform(delete(getPath() + "/" + pathParam)).andExpect(status().isOk());

        verify(service).deleteOrder(pathParam);
        verify(service, times(1)).deleteOrder(pathParam);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFoundWhenDeleteCarWasCalled() throws Exception {
        long pathParam = 1L;

        doThrow(EmptyResultDataAccessException.class).when(service).deleteOrder(anyLong());

        mockMvc.perform(delete(getPath() + "/" + pathParam)).andExpect(status().isNoContent());

        verify(service).deleteOrder(pathParam);
        verify(service, times(1)).deleteOrder(pathParam);
    }
}