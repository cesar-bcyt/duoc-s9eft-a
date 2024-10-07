package com.example.b1efta.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.b1efta.model.Reservation;
import com.example.b1efta.model.Room;
import com.example.b1efta.service.ReservationService;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");

        reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCheckInDate(LocalDate.of(2024, 6, 15));
        reservation.setCheckOutDate(LocalDate.of(2024, 6, 20));
        reservation.setRoom(room);
    }

    @Test
    void getAllReservations_shouldReturnReservationList() throws Exception {
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation));

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.reservationList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.reservationList[0].checkInDate").value("2024-06-15"))
                .andExpect(jsonPath("$._embedded.reservationList[0].checkOutDate").value("2024-06-20"))
                .andExpect(jsonPath("$._embedded.reservationList[0]._links.self.href").value("http://localhost/api/reservations/1"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/reservations"));
    }

    @Test
    void getReservationById_shouldReturnReservation() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

        mockMvc.perform(get("/api/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.checkInDate").value("2024-06-15"))
                .andExpect(jsonPath("$.checkOutDate").value("2024-06-20"))
                .andExpect(jsonPath("$._links.self.href").value("http://localhost/api/reservations/1"))
                .andExpect(jsonPath("$._links.reservations.href").value("http://localhost/api/reservations"));
    }

    @Test
    void deleteReservation_shouldReturnNoContent() throws Exception {
        when(reservationService.getReservationById(1L)).thenReturn(Optional.of(reservation));

        mockMvc.perform(delete("/api/reservations/1"))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).deleteReservation(1L);
    }

    @Test
    void getReservationsByRoomId_shouldReturnReservationList() throws Exception {
        when(reservationService.getReservationsByRoomId(1L)).thenReturn(Arrays.asList(reservation));

        mockMvc.perform(get("/api/reservations/room/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].checkInDate").value("2024-06-15"))
                .andExpect(jsonPath("$[0].checkOutDate").value("2024-06-20"));
    }

    @Test
    void getAvailableDays_shouldReturnAvailableDaysList() throws Exception {
        List<LocalDate> availableDays = Arrays.asList(
            LocalDate.of(2024, 6, 10),
            LocalDate.of(2024, 6, 11),
            LocalDate.of(2024, 6, 12),
            LocalDate.of(2024, 6, 13),
            LocalDate.of(2024, 6, 14),
            LocalDate.of(2024, 6, 20),
            LocalDate.of(2024, 6, 21),
            LocalDate.of(2024, 6, 22),
            LocalDate.of(2024, 6, 23),
            LocalDate.of(2024, 6, 24)
        );

        when(reservationService.getAvailableDaysBetweenDates(1L, LocalDate.of(2024, 6, 10), LocalDate.of(2024, 6, 25)))
                .thenReturn(availableDays);

        mockMvc.perform(get("/api/reservations/availability/1/2024-06-10/2024-06-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("2024-06-10"))
                .andExpect(jsonPath("$[9]").value("2024-06-24"));
    }
}