package com.spring_mvc_json_view.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(scripts = "/schema.sql")
@Sql(scripts = "/data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOrderByIdTest() throws Exception {
        mockMvc.perform(get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("product"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    public void shouldCreateOrderTest() throws Exception {
        mockMvc.perform(post("/orders/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                .content("{\"product\": \"product\", \"amount\": \"100.0\", \"status\": \"pending\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.product").value("product"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    public void shouldUpdateOrderTest() throws Exception {
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\": \"prod\", \"amount\": \"100000.0\", \"status\": \"pending\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product").value("prod"))
                .andExpect(jsonPath("$.amount").value(100000.0))
                .andExpect(jsonPath("$.status").value("pending"));
    }

    @Test
    public void shouldDeleteOrderTest() throws Exception {
        mockMvc.perform(delete("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
