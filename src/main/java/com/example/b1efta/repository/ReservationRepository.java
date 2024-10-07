package com.example.b1efta.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.b1efta.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomId(Long roomId);

    List<Reservation> findByCheckInDateBetweenOrCheckOutDateBetween(
            LocalDate startDate1, LocalDate endDate1,
            LocalDate startDate2, LocalDate endDate2);

    @Query("SELECT r FROM Reservation r WHERE r.room.id = :roomId "
        + " AND ((r.checkInDate BETWEEN :startDate AND :endDate)"
        + "      OR (r.checkOutDate BETWEEN :startDate AND :endDate)"
        + "      OR (:startDate BETWEEN r.checkInDate AND r.checkOutDate))")
    List<Reservation> findByRoomIdAndDateRange(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}