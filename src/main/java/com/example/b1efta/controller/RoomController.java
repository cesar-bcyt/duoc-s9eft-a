package com.example.b1efta.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.example.b1efta.model.Room;
import com.example.b1efta.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private static final Logger logger = LogManager.getLogger(RoomController.class);

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Room>> getAllRooms() {
        List<EntityModel<Room>> rooms = roomService.getAllRooms().stream()
            .map(room -> EntityModel.of(room,
                linkTo(methodOn(RoomController.class).getRoomById(room.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllRooms()).withRel("rooms")))
            .collect(Collectors.toList());

        return CollectionModel.of(rooms, linkTo(methodOn(RoomController.class).getAllRooms()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Room> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id).orElse(null);
        return EntityModel.of(room,
            linkTo(methodOn(RoomController.class).getRoomById(id)).withSelfRel(),
            linkTo(methodOn(RoomController.class).getAllRooms()).withRel("rooms"));
    }

    @GetMapping("/hotel/{hotelId}")
    public List<Room> getRoomsByHotelId(@PathVariable Long hotelId) {
        logger.info("Obteniendo habitaciones para el hotel con id: {}", hotelId);
        return roomService.getRoomsByHotelId(hotelId);
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        logger.info("Creando una nueva habitación");
        return roomService.saveRoom(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        logger.info("Actualizando habitación con id: {}", id);
        return roomService.getRoomById(id)
                .map(existingRoom -> {
                    room.setId(id);
                    return ResponseEntity.ok(roomService.saveRoom(room));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        logger.info("Eliminando habitación con id: {}", id);
        return roomService.getRoomById(id)
                .map(room -> {
                    roomService.deleteRoom(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}