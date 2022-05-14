package me.luandevguerra.rentacar.api.integration.cars;

import io.cucumber.java8.En;
import me.luandevguerra.rentacar.api.models.Car;
import me.luandevguerra.rentacar.api.repository.CarsRepository;
import me.luandevguerra.rentacar.api.service.CarsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static me.luandevguerra.rentacar.api.utils.IntegrationContextData.getContextData;
import static me.luandevguerra.rentacar.api.utils.IntegrationContextData.setContextData;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CarsStepsDef implements En {

    @Autowired
    private CarsRepository repository;

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();

    private String getPath() {
        return "http://localhost:" + port + "/api/v1/cars";
    }

    @Autowired
    protected CarsService service;

    public CarsStepsDef() {


        Given("I have {long} cars at the database", (Long numberOfCars) -> {

            for (int i = 0; i < numberOfCars; i++) {
                repository.save(Car.builder().name("GOL").licensePlate("JUC-7358").build());
            }
        });

        When("I call the resource to obtain all cars", () -> {

            ResponseEntity<Car[]> response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/cars", Car[].class);
            setContextData("responseListSize", response.getBody().length);
        });

        Then("The car list received should have {long} cars", (Long numberOfTheCarsExpected) -> {
            Object responseListSize = getContextData("responseListSize");
//            assertEquals(numberOfTheCarsExpected.longValue(), responseListSize);
        });

    }
}