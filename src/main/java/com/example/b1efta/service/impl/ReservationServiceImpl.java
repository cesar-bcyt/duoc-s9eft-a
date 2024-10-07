package com.example.b1efta.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.b1efta.model.Reservation;
import com.example.b1efta.repository.ReservationRepository;
import com.example.b1efta.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        // Add validation logic here if needed
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> getReservationsByRoomId(Long roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    @Override
    public List<LocalDate> getAvailableDaysBetweenDates(Long roomId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = getReservationsByRoomId(roomId);
        return startDate.datesUntil(endDate.plusDays(1))
                .filter(date -> isDateAvailable(date, reservations))
                .collect(Collectors.toList());
    }

    private boolean isDateAvailable(LocalDate date, List<Reservation> reservations) {
        return reservations.stream()
                .noneMatch(reservation -> 
                    (date.isEqual(reservation.getCheckInDate()) || date.isAfter(reservation.getCheckInDate())) &&
                    (date.isBefore(reservation.getCheckOutDate()) || date.isEqual(reservation.getCheckOutDate()))
                );
    }
}