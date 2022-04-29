package me.shockyng.rentacar.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class AbstractResource {

    protected ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    @Autowired
    protected MockMvc mockMvc;

    protected abstract String getPath();

    protected ArrayList<Object> getDateAsStringArray(LocalDateTime date) {
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
