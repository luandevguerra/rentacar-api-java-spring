package me.luandevguerra.rentacar.api.integration.cars;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(features = { "classpath:features/cars.feature" })
public class CarsIntegrationTest {

}
