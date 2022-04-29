package me.shockyng.rentacar.api.models;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cars")
public class Car {

        @Id
        @Column(name = "car_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "car_name")
        private String name;

        @Column(name = "car_license_plate")
        private String licensePlate;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
                Car car = (Car) o;
                return id != null && Objects.equals(id, car.id);
        }

        @Override
        public int hashCode() {
                return getClass().hashCode();
        }
}