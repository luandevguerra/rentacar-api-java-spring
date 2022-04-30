package me.shockyng.rentacar.api.utils;

import me.shockyng.rentacar.api.dtos.CarDTO;
import me.shockyng.rentacar.api.dtos.OrderDTO;
import me.shockyng.rentacar.api.models.Car;
import me.shockyng.rentacar.api.models.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DataTestProvider {

    public static ArrayList<CarDTO> getCarDTOList() {
        return new ArrayList<>(Arrays.asList(
                CarDTO.builder().id(1L).name("Gol").licensePlate("JHS-9860").build(),
                CarDTO.builder().id(2L).name("Argo").licensePlate("NEY-4856").build()
        ));
    }

    public static ArrayList<Car> getCarList() {
        return new ArrayList<>(Arrays.asList(
                Car.builder().id(1L).name("Gol").licensePlate("JHS-9860").build(),
                Car.builder().id(2L).name("Argo").licensePlate("NEY-4856").build()
        ));
    }

    public static CarDTO getCarDTO(long id) {
        return new CarDTO(id, "nameExample", "licensePlateExample");
    }

    public static Car getCar(long id) {
        return new Car(id, "nameExample", "licensePlateExample");
    }

    public static ArrayList<OrderDTO> getOrderDTOList() {
        return new ArrayList<>(Arrays.asList(
                OrderDTO.builder().id(1L).date(LocalDateTime.now()).price(new BigDecimal(10)).build(),
                OrderDTO.builder().id(2L).date(LocalDateTime.now()).price(new BigDecimal(20)).build()
        ));
    }

    public static OrderDTO getOrderDTO(long id) {
        return OrderDTO.builder().id(1L).date(LocalDateTime.now()).price(new BigDecimal(10)).build();
    }

    public static ArrayList<Order> getOrderList() {
        return new ArrayList<>(Arrays.asList(
                Order.builder().id(1L).date(LocalDateTime.now()).price(new BigDecimal(10)).build(),
                Order.builder().id(2L).date(LocalDateTime.now()).price(new BigDecimal(20)).build()
        ));
    }

    public static Order getOrder(long id) {
        return Order.builder().id(1L).date(LocalDateTime.now()).price(new BigDecimal(10)).build();
    }

    public static ArrayList<Object> getDateAsStringArray(LocalDateTime date) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(date.getYear());
        list.add(date.getMonthValue());
        list.add(date.getDayOfMonth());
        list.add(date.getHour());
        list.add(date.getMinute());
        list.add(date.getSecond());
        list.add(date.getNano());
        return list;
    }
}
