package com.example.b1efta.controller;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.b1efta.controller.RoomController;
import com.example.b1efta.model.Hotel;
import com.example.b1efta.model.Room;
import com.example.b1efta.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    private Room room;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");

        room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");
        room.setHotel(hotel);
    }

    @Test
    void getAllRooms_shouldReturnRoomList() throws Exception {
        when(roomService.getAllRooms()).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.roomList[0].roomNumber").value("101"))
                .andExpect(jsonPath("$._embedded.roomList[0]._links.self.href").value("http://localhost/api/rooms/1"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/rooms"));
    }

    @Test
    void getRoomById_shouldReturnRoom() throws Exception {
        when(roomService.getRoomById(1L)).thenReturn(Optional.of(room));

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomNumber").value("101"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/rooms/1"))
                .andExpect(jsonPath("$._links.rooms.href").value("http://localhost/api/rooms"));
    }

    @Test
    void createRoom_shouldReturnCreatedRoom() throws Exception {
        when(roomService.saveRoom(any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/api/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomNumber").value("101"));
    }

    @Test
    void updateRoom_shouldReturnUpdatedRoom() throws Exception {
        when(roomService.getRoomById(1L)).thenReturn(Optional.of(room));
        when(roomService.saveRoom(any(Room.class))).thenReturn(room);

        mockMvc.perform(put("/api/rooms/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.roomNumber").value("101"));
    }

    @Test
    void deleteRoom_shouldReturnNoContent() throws Exception {
        when(roomService.getRoomById(1L)).thenReturn(Optional.of(room));

        mockMvc.perform(delete("/api/rooms/1"))
                .andExpect(status().isOk());

        verify(roomService, times(1)).deleteRoom(1L);
    }

    @Test
    void getRoomsByHotelId_shouldReturnRoomList() throws Exception {
        when(roomService.getRoomsByHotelId(1L)).thenReturn(Arrays.asList(room));

        mockMvc.perform(get("/api/rooms/hotel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].roomNumber").value("101"));
    }
}