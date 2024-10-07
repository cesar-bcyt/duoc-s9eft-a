package com.example.b1efta.service;

import java.util.List;
import java.util.Optional;

import com.example.b1efta.model.Hotel;

public interface HotelService {
    List<Hotel> getAllHotels();
    Optional<Hotel> getHotelById(Long id);
    Hotel saveHotel(Hotel hotel);
    void deleteHotel(Long id);
}