package me.luandevguerra.rentacar.api.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarDTO {

    private Long id;
    private String name;
    private String licensePlate;

    public CarDTO(Long id, String name, String licensePlate) {
        this.id = id;
        this.name = name;
        this.licensePlate = licensePlate;
    }

    public CarDTO() {
    }
}
