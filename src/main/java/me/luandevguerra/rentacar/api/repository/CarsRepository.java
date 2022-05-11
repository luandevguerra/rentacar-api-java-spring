package me.luandevguerra.rentacar.api.repository;

import me.luandevguerra.rentacar.api.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepository extends JpaRepository<Car, Long> {
}
