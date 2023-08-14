package com.terratech.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terratech.api.dto.collector.CollectorRequest;
import com.terratech.api.dto.collector.CollectorResponse;
import com.terratech.api.dto.collector.CollectorUpdate;
import com.terratech.api.error.exceptions.NotFoundException;
import com.terratech.api.model.Collector;
import com.terratech.api.services.CollectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@WebMvcTest(CollectorController.class)
public class CollectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CollectorService collectorService;

    private Collector collector;

    @BeforeEach
    void setup() {
         collector = Collector.builder()
                .id(1L)
                .name("Teste")
                .email("teste@gmail.com")
                .password("teste123@")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .build();
    }

    @Test
    void shouldGetOne() throws  Exception {
        when(this.collectorService.findById(anyLong())).thenReturn(new CollectorResponse(collector));

        String collectorWriteAsString = objectMapper.writeValueAsString(new CollectorResponse(collector));

        mockMvc.perform(get("/v1/collectors/1")
                        .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(collectorWriteAsString));
    }

    @Test
    void  shouldNotGetOne () throws  Exception{
        when(this.collectorService.findById(anyLong()))
                .thenThrow(new NotFoundException("Collector not found"));


        mockMvc.perform(get("/v1/collectors/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Collector not found"));
    }

    @Test
    void shouldUpdated()  throws Exception{
        collector.setEmail("update@email.com");

        doNothing().when(this.collectorService).update(anyLong(), any(CollectorUpdate.class));

        var request = new CollectorUpdate(collector);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/v1/collectors/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("SUCCESSFULLY UPDATED COLLECTOR"));
    }

    @Test
    void shouldNotUpdated()  throws Exception{
        doThrow(new NotFoundException("Collector not found"))
                .when(this.collectorService)
                .update(anyLong(), any(CollectorUpdate.class));

        var request = new CollectorUpdate(collector);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/v1/collectors/1")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Collector not found"));
    }

    @Test
    void shouldDelete()  throws Exception{
        doNothing().when(this.collectorService).delete(anyLong());

        mockMvc.perform(delete("/v1/collectors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("SUCCESSFULLY DELETED COLLECTOR"));

    }

    @Test
    void shouldNotDelete()  throws Exception{
        doThrow(new NotFoundException("Collector not found"))
                .when(this.collectorService)
                .delete(anyLong());

        mockMvc.perform(delete("/v1/collectors/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("Collector not found"));
    }






}