package me.shockyng.rentacar.api.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Data
public class OrderDTO {

    private Long id;
    private BigDecimal price;

//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime date;

    public OrderDTO(Long id, BigDecimal price, LocalDateTime date) {
        this.id = id;
        this.price = price;
        this.date = date;
    }

    public OrderDTO() {
    }
}