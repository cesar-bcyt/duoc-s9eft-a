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

import com.example.b1efta.model.Hotel;
import com.example.b1efta.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private static final Logger logger = LogManager.getLogger(HotelController.class);

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public CollectionModel<EntityModel<Hotel>> getAllHotels() {
        List<EntityModel<Hotel>> hotels = hotelService.getAllHotels().stream()
            .map(hotel -> EntityModel.of(hotel,
                linkTo(methodOn(HotelController.class).getHotelById(hotel.getId())).withSelfRel(),
                linkTo(methodOn(HotelController.class).getAllHotels()).withRel("hotels")))
            .collect(Collectors.toList());

        return CollectionModel.of(hotels, linkTo(methodOn(HotelController.class).getAllHotels()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Hotel> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id).orElse(null);
        return EntityModel.of(hotel,
            linkTo(methodOn(HotelController.class).getHotelById(id)).withSelfRel(),
            linkTo(methodOn(HotelController.class).getAllHotels()).withRel("hotels"));
    }

    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        logger.info("Creando un nuevo hotel");
        Hotel savedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(savedHotel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        logger.info("Actualizando hotel con id: {}", id);
        return hotelService.getHotelById(id)
                .map(existingHotel -> {
                    hotel.setId(id);
                    return ResponseEntity.ok(hotelService.saveHotel(hotel));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        logger.info("Eliminando hotel con id: {}", id);
        return hotelService.getHotelById(id)
                .map(hotel -> {
                    hotelService.deleteHotel(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}