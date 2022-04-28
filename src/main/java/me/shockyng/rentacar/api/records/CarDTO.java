package me.shockyng.rentacar.api.records;

import lombok.Data;

@Data
public class CarDTO {

    private Long id;
    private String name;
    private String licensePlate;
}
