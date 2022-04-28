package me.shockyng.rentacar.api.records;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class OrderDTO {

    private Long id;
    private BigDecimal price;
    private LocalDateTime date;
}