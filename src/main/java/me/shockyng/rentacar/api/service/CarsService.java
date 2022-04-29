package me.shockyng.rentacar.api.service;

import me.shockyng.rentacar.api.exceptions.CarNotFoundException;
import me.shockyng.rentacar.api.models.Car;
import me.shockyng.rentacar.api.records.CarDTO;
import me.shockyng.rentacar.api.repository.CarsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsService {

    private final ModelMapper mapper = new ModelMapper();

    private final CarsRepository repository;

    public CarsService(CarsRepository repository) {
        this.repository = repository;
    }

    public ArrayList<CarDTO> getCars() {
        List<Car> cars = repository.findAll();
        if (cars.isEmpty()) throw new CarNotFoundException();
        else return (ArrayList<CarDTO>) cars.stream().map(c -> mapper.map(c, CarDTO.class))
                .collect(Collectors.toList());
    }

    public CarDTO getCar(Long id) {
        return mapper.map(repository.findById(id).get(), CarDTO.class);
    }

    public CarDTO createCar(CarDTO car) {
        return mapper.map(repository.save(mapper.map(car, Car.class)), CarDTO.class);
    }

    public CarDTO updateCar(Long id, CarDTO carDTO) {
        Car car = mapper.map(carDTO, Car.class);
        car.setId(getCar(id).getId());
        return mapper.map(repository.save(car), CarDTO.class);
    }

    public void deleteCar(Long id) {
        repository.deleteById(id);
    }
}
