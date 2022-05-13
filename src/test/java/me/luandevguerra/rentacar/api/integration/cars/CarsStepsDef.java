package me.luandevguerra.rentacar.api.integration.cars;

import io.cucumber.java.en.Given;
import io.cucumber.java8.En;
import me.luandevguerra.rentacar.api.models.Car;
import me.luandevguerra.rentacar.api.service.CarsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CarsStepsDef implements En {

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getPath() {
        return "http://localhost:" + port +"/api/v1/cars";
    }

    @Autowired
    protected CarsService service;

    public CarsStepsDef() {

        Given("I have two cars at the database", () -> {

        });

        When("I call the resource to obtain all cars", () -> {
            ResponseEntity<Car[]> forEntity = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/cars", Car[].class);
        });

    }

}