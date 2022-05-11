package me.luandevguerra.rentacar.api.resources;

import me.luandevguerra.rentacar.api.dtos.CarDTO;
import me.luandevguerra.rentacar.api.exceptions.CarNotFoundException;
import me.luandevguerra.rentacar.api.service.CarsService;
import me.luandevguerra.rentacar.api.utils.AbstractResourceTest;
import me.luandevguerra.rentacar.api.utils.DataTestProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CarsResourceTestTest extends AbstractResourceTest {

    @Mock
    private CarsService service;

    @InjectMocks
    private CarsResource carsResource;

    @Autowired
    private MockMvc mockMvc;

    @Override
    protected String getPath() {
        return "/v1/cars";
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carsResource).build();
    }

    @Test
    void shouldReturnAListWithCars() throws Exception {
        ArrayList<CarDTO> carList = DataTestProvider.getCarDTOList();

        when(service.getCars()).thenReturn(carList);

        mockMvc.perform(get(getPath()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(carList.get(0).getId()))
                .andExpect(jsonPath("$[0].name").value(carList.get(0).getName()))
                .andExpect(jsonPath("$[0].licensePlate").value(carList.get(0).getLicensePlate()))
                .andExpect(jsonPath("$[1].id").value(carList.get(1).getId()))
                .andExpect(jsonPath("$[1].name").value(carList.get(1).getName()))
                .andExpect(jsonPath("$[1].licensePlate").value(carList.get(1).getLicensePlate()))
                .andReturn();

        verify(service).getCars();
        verify(service, times(1)).getCars();
    }

    @Test
    void shouldReturn204CodeOnceNoCarsWereFoundWhenGetCarsWasCalled() throws Exception {
        when(service.getCars()).thenThrow(CarNotFoundException.class);

        mockMvc.perform(get(getPath())).andExpect(status().isNoContent());

        verify(service).getCars();
        verify(service, times(1)).getCars();
    }

    @Test
    void shouldReturn200CodeAndACar() throws Exception {
        long pathParam = 1L;
        CarDTO car = DataTestProvider.getCarDTO(pathParam);
        when(service.getCar(anyLong())).thenReturn(car);

        mockMvc.perform(get(getPath() + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.name").value(car.getName()))
                .andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()));

        verify(service).getCar(pathParam);
        verify(service, times(1)).getCar(pathParam);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFound() throws Exception {
        long pathParam = 1L;
        when(service.getCar(anyLong())).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(getPath() + "/" + pathParam)).andExpect(status().isNoContent());

        verify(service).getCar(pathParam);
        verify(service, times(1)).getCar(pathParam);
    }


    @Test
    void shouldReturn201CodeAndACarOnResponseOnceACarWasCreated() throws Exception {
        CarDTO car = DataTestProvider.getCarDTO(1L);

        when(service.createCar(any(CarDTO.class))).thenReturn(car);

        mockMvc.perform(post(getPath()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(car.getName()))
                .andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()))
                .andReturn();

        verify(service).createCar(car);
        verify(service, times(1)).createCar(car);
    }

    @Test
    void shouldReturn201CodeAndACarOnResponseOnceACarWasUpdated() throws Exception {
        long pathParam = 1L;
        CarDTO car = DataTestProvider.getCarDTO(pathParam);

        when(service.updateCar(anyLong(), any(CarDTO.class))).thenReturn(car);

        mockMvc.perform(put(getPath() + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.name").value(car.getName()))
                .andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()))
                .andReturn();

        verify(service).updateCar(pathParam, car);
        verify(service, times(1)).updateCar(pathParam, car);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFoundWhenUpdateCarIsCalled() throws Exception {
        long pathParam = 1L;
        CarDTO car = DataTestProvider.getCarDTO(pathParam);

        when(service.updateCar(anyLong(), any(CarDTO.class))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(getPath() + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isNoContent())
                .andReturn();

        verify(service).updateCar(pathParam, car);
        verify(service, times(1)).updateCar(pathParam, car);
    }

    @Test
    void shouldReturn200CodeOnceACarWasDeleted() throws Exception {
        long pathParam = 1L;

        doNothing().when(service).deleteCar(anyLong());

        mockMvc.perform(delete(getPath() + "/" + pathParam)).andExpect(status().isOk());

        verify(service).deleteCar(pathParam);
        verify(service, times(1)).deleteCar(pathParam);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFoundWhenDeleteCarWasCalled() throws Exception {
        long pathParam = 1L;

        doThrow(EmptyResultDataAccessException.class).when(service).deleteCar(anyLong());

        mockMvc.perform(delete(getPath() + "/" + pathParam)).andExpect(status().isNoContent());

        verify(service).deleteCar(pathParam);
        verify(service, times(1)).deleteCar(pathParam);
    }

}