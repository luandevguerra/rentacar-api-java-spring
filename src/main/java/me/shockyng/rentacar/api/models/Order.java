package me.shockyng.rentacar.api.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

        @Id
        @Column(name = "order_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "order_price")
        private BigDecimal price;

        @Column(name = "order_date")
        private LocalDateTime date;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Order)) return false;
                Order order = (Order) o;
                return Objects.equals(getId(), order.getId()) && Objects.equals(getPrice(), order.getPrice()) && Objects.equals(getDate(), order.getDate());
        }

        @Override
        public int hashCode() {
                return Objects.hash(getId(), getPrice(), getDate());
        }
}