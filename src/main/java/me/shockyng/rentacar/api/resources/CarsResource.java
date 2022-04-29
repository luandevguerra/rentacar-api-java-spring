package me.shockyng.rentacar.api.resources;

import me.shockyng.rentacar.api.exceptions.CarNotFoundException;
import me.shockyng.rentacar.api.records.CarDTO;
import me.shockyng.rentacar.api.service.CarsService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("v1/cars")
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
    public ResponseEntity<CarDTO> getCar(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(service.getCar(id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO car) {
        return new ResponseEntity<>(service.createCar(car), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable("id") Long id, @RequestBody CarDTO carDTO) {
        try {
            return new ResponseEntity<>(service.updateCar(id, carDTO), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDTO> deleteACar(@PathVariable("id") Long id) {
        try {
            service.deleteCar(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
