package me.luandevguerra.rentacar.api.integration.cars;

import io.cucumber.java8.En;
import me.luandevguerra.rentacar.api.models.Car;
import me.luandevguerra.rentacar.api.utils.IntegrationContextData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.ArrayList;

public class CarsStepsDef implements En {

    private final String CARS_LIST = "carsList";

    @Autowired
    protected TestRestTemplate template;

    public CarsStepsDef() {

        Given("I have two cars at the database", () -> {
            ArrayList<Car> cars = new ArrayList<>();
            Car car1 = Car.builder().id(1L).name("Gol").licensePlate("ABC-123").build();
            Car car2 = Car.builder().id(2L).name("Argo").licensePlate("DEF-456").build();
            cars.add(car1);
            cars.add(car2);
            IntegrationContextData.setContextData(CARS_LIST, cars);
        });

    }

}