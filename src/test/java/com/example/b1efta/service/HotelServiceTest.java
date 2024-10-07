package com.example.b1efta.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.b1efta.model.Hotel;
import com.example.b1efta.repository.HotelRepository;
import com.example.b1efta.service.impl.HotelServiceImpl;

public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetAllHotels_thenReturnHotelList() {
        // given
        Hotel hotel1 = new Hotel();
        hotel1.setId(1L);
        hotel1.setName("Hotel 1");

        Hotel hotel2 = new Hotel();
        hotel2.setId(2L);
        hotel2.setName("Hotel 2");

        List<Hotel> expectedHotels = Arrays.asList(hotel1, hotel2);

        when(hotelRepository.findAll()).thenReturn(expectedHotels);

        // when
        List<Hotel> actualHotels = hotelService.getAllHotels();

        // then
        assertThat(actualHotels).isEqualTo(expectedHotels);
    }

    @Test
    public void whenGetHotelById_thenReturnHotel() {
        // given
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        // when
        Optional<Hotel> foundHotel = hotelService.getHotelById(1L);

        // then
        assertThat(foundHotel).isPresent();
        assertThat(foundHotel.get().getName()).isEqualTo("Test Hotel");
    }

    @Test
    public void whenSaveHotel_thenReturnSavedHotel() {
        // given
        Hotel hotel = new Hotel();
        hotel.setName("New Hotel");

        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);

        // when
        Hotel savedHotel = hotelService.saveHotel(hotel);

        // then
        assertThat(savedHotel.getName()).isEqualTo("New Hotel");
        verify(hotelRepository).save(hotel);
    }

    @Test
    public void whenDeleteHotel_thenRepositoryMethodCalled() {
        // given
        Long hotelId = 1L;

        // when
        hotelService.deleteHotel(hotelId);

        // then
        verify(hotelRepository).deleteById(hotelId);
    }
}