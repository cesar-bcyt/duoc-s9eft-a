package com.example.b1efta.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.b1efta.model.Reservation;
import com.example.b1efta.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private static final Logger logger = LogManager.getLogger(ReservationController.class);

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Reservation>> getAllReservations() {
        List<EntityModel<Reservation>> reservations = reservationService.getAllReservations().stream()
            .map(reservation -> EntityModel.of(reservation,
                linkTo(methodOn(ReservationController.class).getReservationById(reservation.getId())).withSelfRel(),
                linkTo(methodOn(ReservationController.class).getAllReservations()).withRel("reservations")))
            .collect(Collectors.toList());

        return CollectionModel.of(reservations, linkTo(methodOn(ReservationController.class).getAllReservations()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservationById(id).orElse(null);
        return EntityModel.of(reservation,
            linkTo(methodOn(ReservationController.class).getReservationById(id)).withSelfRel(),
            linkTo(methodOn(ReservationController.class).getAllReservations()).withRel("reservations"));
    }

    @GetMapping("/room/{roomId}")
    public List<Reservation> getReservationsByRoomId(@PathVariable Long roomId) {
        logger.info("Obteniendo reservas para la habitación con id: {}", roomId);
        return reservationService.getReservationsByRoomId(roomId);
    }

    @GetMapping("/availability/{roomId}/{startDate}/{endDate}")
    public List<LocalDate> getAvailableDays(
            @PathVariable Long roomId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        logger.info("Verificando disponibilidad para la habitación con id: {} entre {} y {}", roomId, startDate, endDate);
        return reservationService.getAvailableDaysBetweenDates(roomId, startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@Valid @RequestBody Reservation reservation) {
        logger.info("Creando una nueva reserva");
        try {
            Reservation savedReservation = reservationService.saveReservation(reservation);
            return ResponseEntity.ok(savedReservation);
        } catch (IllegalStateException e) {
            logger.error("Error al crear la reserva: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @Valid @RequestBody Reservation reservation) {
        logger.info("Actualizando reserva con id: {}", id);
        return reservationService.getReservationById(id)
                .map(existingReservation -> {
                    reservation.setId(id);
                    try {
                        return ResponseEntity.ok(reservationService.saveReservation(reservation));
                    } catch (IllegalStateException e) {
                        logger.error("Error al actualizar la reserva: {}", e.getMessage());
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        logger.info("Eliminando reserva con id: {}", id);
        return reservationService.getReservationById(id)
                .map(reservation -> {
                    reservationService.deleteReservation(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}