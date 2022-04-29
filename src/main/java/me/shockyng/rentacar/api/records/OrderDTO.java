package me.shockyng.rentacar.api.records;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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