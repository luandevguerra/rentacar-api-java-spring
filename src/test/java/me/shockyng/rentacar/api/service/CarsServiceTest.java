package me.shockyng.rentacar.api.service;

import me.shockyng.rentacar.api.dtos.CarDTO;
import me.shockyng.rentacar.api.exceptions.CarNotFoundException;
import me.shockyng.rentacar.api.models.Car;
import me.shockyng.rentacar.api.repository.CarsRepository;
import me.shockyng.rentacar.api.utils.DataTestProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarsServiceTest {

    private final ModelMapper mapper = new ModelMapper();

    @Mock
    private CarsRepository repository;

    @InjectMocks
    private CarsService service;

    @Test
    void shouldReturnACarList() {
        ArrayList<Car> carList = DataTestProvider.getCarList();

        when(repository.findAll()).thenReturn(carList);

        ArrayList<CarDTO> carListDTO = service.getCars();

        assertEquals(carList.size(), carListDTO.size());
        assertEquals(carList.get(0).getId(), carListDTO.get(0).getId());
        assertEquals(carList.get(0).getId(), carListDTO.get(0).getId());
        assertEquals(carList.get(0).getName(), carListDTO.get(0).getName());
        assertEquals(carList.get(0).getLicensePlate(), carListDTO.get(0).getLicensePlate());
    }

    @Test
    void shouldThrowCarNotFoundExceptionOnceNoCarsWereFound() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(CarNotFoundException.class, () -> service.getCars());
    }

    @Test
    void shouldReturnACarWhenGetCarIsCalled() {
        Long pathParam = 1L;
        Car car = DataTestProvider.getCar(pathParam);

        when(repository.findById(pathParam)).thenReturn(Optional.of(car));

        CarDTO carDTO = service.getCar(pathParam);

        assertEquals(car.getId(), carDTO.getId());
        assertEquals(car.getName(), carDTO.getName());
        assertEquals(car.getLicensePlate(), carDTO.getLicensePlate());
    }

    @Test
    void shouldThrowCarNotFoundExceptionOnceNoCarWasFoundWhenGetCarIsCalled() {
        Long pathParam = 1L;

        when(repository.findById(pathParam)).thenThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> service.getCar(pathParam));
    }

    @Test
    void shouldReturnACarWhenCreateCarIsCalled() {
        long id = 1L;
        Car car = DataTestProvider.getCar(id);
        CarDTO carDTO = mapper.map(car, CarDTO.class);

        when(repository.save(car)).thenReturn(car);

        CarDTO car1 = service.createCar(carDTO);

        assertEquals(car1.getId(), carDTO.getId());
        assertEquals(car1.getName(), carDTO.getName());
        assertEquals(car1.getLicensePlate(), carDTO.getLicensePlate());
    }

    @Test
    void shouldReturnACarWhenUpdateCarIsCalled() {
        long id = 1L;
        Car car = DataTestProvider.getCar(id);
        CarDTO carDTO = DataTestProvider.getCarDTO(id);

        when(repository.findById(id)).thenReturn(Optional.of(car));
        when(repository.save(car)).thenReturn(car);

        CarDTO car1 = service.updateCar(id, carDTO);

        assertEquals(car1.getId(), carDTO.getId());
        assertEquals(car1.getName(), carDTO.getName());
        assertEquals(car1.getLicensePlate(), carDTO.getLicensePlate());
    }

    @Test
    void shouldThrowCarNotFoundExceptionWhenUpdateCarIsCalled() {
        long id = 1L;
        CarDTO carDTO = DataTestProvider.getCarDTO(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.updateCar(id, carDTO));
    }

    @Test
    void shouldReturnThrowExceptionOnceNoCarWasFound() {
        long id = 1L;

        doThrow(NoSuchElementException.class).when(repository).deleteById(id);

        assertThrows(NoSuchElementException.class, () -> service.deleteCar(id));
    }

    @Test
    void shouldReturnNothingOnceDeleteByIdWasExecutedSuccessfully() {
        long id = 1L;

        doNothing().when(repository).deleteById(id);

        service.deleteCar(id);
    }
}