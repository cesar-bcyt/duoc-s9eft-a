package com.example.b1efta.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.b1efta.controller.HotelController;
import com.example.b1efta.model.Hotel;
import com.example.b1efta.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");
    }

    @Test
    void getAllHotels_shouldReturnHotelList() throws Exception {
        when(hotelService.getAllHotels()).thenReturn(Arrays.asList(hotel));

        mockMvc.perform(get("/api/hotels"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.hotelList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.hotelList[0].name").value("Test Hotel"))
                .andExpect(jsonPath("$._embedded.hotelList[0]._links.self.href").value("http://localhost/api/hotels/1"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/hotels"));
    }

    @Test
    void getHotelById_shouldReturnHotel() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(Optional.of(hotel));

        mockMvc.perform(get("/api/hotels/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/hotels/1"))
                .andExpect(jsonPath("$._links.hotels.href").value("http://localhost/api/hotels"));
    }

    @Test
    void createHotel_shouldReturnCreatedHotel() throws Exception {
        when(hotelService.saveHotel(any(Hotel.class))).thenReturn(hotel);

        mockMvc.perform(post("/api/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    void updateHotel_shouldReturnUpdatedHotel() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(Optional.of(hotel));
        when(hotelService.saveHotel(any(Hotel.class))).thenReturn(hotel);

        mockMvc.perform(put("/api/hotels/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hotel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    void deleteHotel_shouldReturnNoContent() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(Optional.of(hotel));

        mockMvc.perform(delete("/api/hotels/1"))
                .andExpect(status().isOk());

        verify(hotelService, times(1)).deleteHotel(1L);
    }
}