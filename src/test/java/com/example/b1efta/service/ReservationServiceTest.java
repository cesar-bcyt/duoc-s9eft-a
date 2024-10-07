package com.example.b1efta.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.b1efta.model.Reservation;
import com.example.b1efta.model.Room;
import com.example.b1efta.repository.ReservationRepository;
import com.example.b1efta.service.impl.ReservationServiceImpl;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetAllReservations_thenReturnReservationList() {
        // given
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findAll()).thenReturn(expectedReservations);

        // when
        List<Reservation> actualReservations = reservationService.getAllReservations();

        // then
        assertThat(actualReservations).isEqualTo(expectedReservations);
    }

    @Test
    public void whenGetReservationById_thenReturnReservation() {
        // given
        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setCheckInDate(LocalDate.now());
        reservation.setCheckOutDate(LocalDate.now().plusDays(2));

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        // when
        Optional<Reservation> foundReservation = reservationService.getReservationById(1L);

        // then
        assertThat(foundReservation).isPresent();
        assertThat(foundReservation.get().getCheckInDate()).isEqualTo(reservation.getCheckInDate());
        assertThat(foundReservation.get().getCheckOutDate()).isEqualTo(reservation.getCheckOutDate());
    }

    @Test
    public void whenSaveReservation_thenReturnSavedReservation() {
        // given
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(LocalDate.now());
        reservation.setCheckOutDate(LocalDate.now().plusDays(2));

        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // when
        Reservation savedReservation = reservationService.saveReservation(reservation);

        // then
        assertThat(savedReservation.getCheckInDate()).isEqualTo(reservation.getCheckInDate());
        assertThat(savedReservation.getCheckOutDate()).isEqualTo(reservation.getCheckOutDate());
        verify(reservationRepository).save(reservation);
    }

    @Test
    public void whenDeleteReservation_thenRepositoryMethodCalled() {
        // given
        Long reservationId = 1L;

        // when
        reservationService.deleteReservation(reservationId);

        // then
        verify(reservationRepository).deleteById(reservationId);
    }

    @Test
    public void whenGetReservationsByRoomId_thenReturnReservationList() {
        // given
        Room room = new Room();
        room.setId(1L);
        Reservation reservation1 = new Reservation();
        reservation1.setRoom(room);
        Reservation reservation2 = new Reservation();
        reservation2.setRoom(room);
        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

        when(reservationRepository.findByRoomId(1L)).thenReturn(expectedReservations);

        // when
        List<Reservation> actualReservations = reservationService.getReservationsByRoomId(1L);

        // then
        assertThat(actualReservations).isEqualTo(expectedReservations);
    }

    @Test
    public void whenGetAvailableDaysBetweenDates_thenReturnAvailableDaysList() {
        // given
        Room room = new Room();
        room.setId(1L);
        Reservation reservation = new Reservation();
        reservation.setRoom(room);
        reservation.setCheckInDate(LocalDate.of(2024,9, 15));
        reservation.setCheckOutDate(LocalDate.of(2024,9, 20));

        when(reservationRepository.findByRoomId(1L)).thenReturn(Arrays.asList(reservation));

        // when
        List<LocalDate> availableDays = reservationService.getAvailableDaysBetweenDates(1L, 
            LocalDate.of(2024, 9, 10), LocalDate.of(2024, 9, 25));

        // then
        assertThat(availableDays).contains(
            LocalDate.of(2024, 9, 10),
            LocalDate.of(2024, 9, 11),
            LocalDate.of(2024, 9, 12),
            LocalDate.of(2024, 9, 13),
            LocalDate.of(2024, 9, 14),
            // No devuelve los días entre el 15 y el 20
            LocalDate.of(2024, 9, 21),
            LocalDate.of(2024, 9, 22),
            LocalDate.of(2024, 9, 23),
            LocalDate.of(2024, 9, 24),
            LocalDate.of(2024, 9, 25)
        );
    }
}