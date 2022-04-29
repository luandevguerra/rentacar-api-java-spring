package me.shockyng.rentacar.api.service;

import me.shockyng.rentacar.api.dtos.CarDTO;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarsServiceTest {

    @Mock
    private CarsRepository repository;

    @InjectMocks
    private CarsService service;

    @Test
    void shouldReturnACarList() {
        ArrayList<Car> carList = DataTestProvider.getCarList();

        when(repository.findAll()).thenReturn(carList);

        ArrayList<CarDTO> cars = service.getCars();

        assertEquals(carList.get(0).getId(), cars.get(0).getId());
        assertEquals(carList.get(0).getName(), cars.get(0).getName());
        assertEquals(carList.get(0).getLicensePlate(), cars.get(0).getLicensePlate());
    }

    @Test
    void getCar() {
    }

    @Test
    void createCar() {
    }

    @Test
    void updateCar() {
    }

    @Test
    void deleteCar() {
    }
}