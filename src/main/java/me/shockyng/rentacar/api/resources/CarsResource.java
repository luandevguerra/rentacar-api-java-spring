package me.shockyng.rentacar.api.resources;

import me.shockyng.rentacar.api.records.CarDTO;
import me.shockyng.rentacar.api.service.CarsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/cars")
public class CarsResource {

    private final CarsService service;

    public CarsResource(CarsService service) {
        this.service = service;
    }

    @GetMapping
    public ArrayList<CarDTO> getCars() {
        return service.getCars();
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable("id") Long id) {
        return service.getCar(id);
    }

    @PostMapping
    public CarDTO createCar(@RequestBody CarDTO car) {
        return service.createCar(car);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable("id") Long id, @RequestBody CarDTO carDTO) {
        return service.updateCar(id, carDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteACar(@PathVariable("id") Long id) {
        service.deleteCar(id);
    }


}
