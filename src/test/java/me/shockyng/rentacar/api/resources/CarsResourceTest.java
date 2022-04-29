package me.shockyng.rentacar.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.shockyng.rentacar.api.exceptions.CarNotFoundException;
import me.shockyng.rentacar.api.records.CarDTO;
import me.shockyng.rentacar.api.service.CarsService;
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
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CarsResourceTest {

    ObjectMapper objectMapper = new ObjectMapper();

    private final String URI = "/v1/cars";

    @Mock
    private CarsService service;

    @InjectMocks
    private CarsResource carsResource;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(carsResource).build();
    }

    @Test
    void shouldReturnAListWithCars() throws Exception {
        ArrayList<CarDTO> carList = getCarList();

        when(service.getCars()).thenReturn(carList);

        mockMvc.perform(get(URI).contentType(MediaType.APPLICATION_JSON))
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

        mockMvc.perform(get(URI)).andExpect(status().isNoContent());

        verify(service).getCars();
        verify(service, times(1)).getCars();
    }

    @Test
    void shouldReturn200CodeAndACar() throws Exception {
        long pathParam = 1L;
        CarDTO car = getCar(pathParam);
        when(service.getCar(anyLong())).thenReturn(car);

        mockMvc.perform(get(URI + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
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

        mockMvc.perform(get(URI + "/" + pathParam)).andExpect(status().isNoContent());

        verify(service).getCar(pathParam);
        verify(service, times(1)).getCar(pathParam);
    }


    @Test
    void shouldReturn201CodeAndACarOnResponseOnceACarWasCreated() throws Exception {
        CarDTO car = getCar(1L);

        when(service.createCar(any(CarDTO.class))).thenReturn(car);

        mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(car)))
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
        CarDTO car = getCar(pathParam);

        when(service.updateCar(anyLong(), any(CarDTO.class))).thenReturn(car);

        mockMvc.perform(put(URI + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
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
        CarDTO car = getCar(pathParam);

        when(service.updateCar(anyLong(), any(CarDTO.class))).thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(URI + "/" + pathParam).contentType(MediaType.APPLICATION_JSON)
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

        mockMvc.perform(delete(URI + "/" + pathParam)).andExpect(status().isOk());

        verify(service).deleteCar(pathParam);
        verify(service, times(1)).deleteCar(pathParam);
    }

    @Test
    void shouldReturn204CodeOnceNoCarWasFoundWhenDeleteCarWasCalled() throws Exception {
        long pathParam = 1L;

        doThrow(EmptyResultDataAccessException.class).when(service).deleteCar(anyLong());

        mockMvc.perform(delete(URI + "/" + pathParam)).andExpect(status().isNoContent());

        verify(service).deleteCar(pathParam);
        verify(service, times(1)).deleteCar(pathParam);
    }

    private ArrayList<CarDTO> getCarList() {
        return new ArrayList<>(Arrays.asList(
                CarDTO.builder().id(1L).name("Gol").licensePlate("JHS-9860").build(),
                CarDTO.builder().id(2L).name("Argo").licensePlate("NEY-4856").build()
        ));
    }

    private CarDTO getCar(long id) {
        return new CarDTO(id, "nameExample", "licensePlateExample");
    }
}