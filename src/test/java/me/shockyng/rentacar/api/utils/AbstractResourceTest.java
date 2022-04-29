package me.shockyng.rentacar.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class AbstractResourceTest {

    protected ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());;

    @Autowired
    protected MockMvc mockMvc;

    protected abstract String getPath();

}
