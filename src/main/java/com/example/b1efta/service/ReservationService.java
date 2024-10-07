package com.example.b1efta.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.b1efta.model.Reservation;

public interface ReservationService {
    List<Reservation> getAllReservations();
    Optional<Reservation> getReservationById(Long id);
    Reservation saveReservation(Reservation reservation);
    void deleteReservation(Long id);
    List<Reservation> getReservationsByRoomId(Long roomId);
    List<LocalDate> getAvailableDaysBetweenDates(Long roomId, LocalDate startDate, LocalDate endDate);
}