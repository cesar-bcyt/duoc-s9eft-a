package com.example.b1efta.service;

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

import com.example.b1efta.model.Hotel;
import com.example.b1efta.model.Room;
import com.example.b1efta.repository.RoomRepository;
import com.example.b1efta.service.impl.RoomServiceImpl;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetAllRooms_thenReturnRoomList() {
        // given
        Room room1 = new Room();
        room1.setId(1L);
        room1.setRoomNumber("101");
        Room room2 = new Room();
        room2.setId(2L);
        room2.setRoomNumber("102");
        List<Room> expectedRooms = Arrays.asList(room1, room2);

        when(roomRepository.findAll()).thenReturn(expectedRooms);

        // when
        List<Room> actualRooms = roomService.getAllRooms();

        // then
        assertThat(actualRooms).isEqualTo(expectedRooms);
    }

    @Test
    public void whenGetRoomById_thenReturnRoom() {
        // given
        Room room = new Room();
        room.setId(1L);
        room.setRoomNumber("101");

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // when
        Optional<Room> foundRoom = roomService.getRoomById(1L);

        // then
        assertThat(foundRoom).isPresent();
        assertThat(foundRoom.get().getRoomNumber()).isEqualTo("101");
    }

    @Test
    public void whenSaveRoom_thenReturnSavedRoom() {
        // given
        Room room = new Room();
        room.setRoomNumber("101");

        when(roomRepository.save(any(Room.class))).thenReturn(room);

        // when
        Room savedRoom = roomService.saveRoom(room);

        // then
        assertThat(savedRoom.getRoomNumber()).isEqualTo("101");
        verify(roomRepository).save(room);
    }

    @Test
    public void whenDeleteRoom_thenRepositoryMethodCalled() {
        // given
        Long roomId = 1L;

        // when
        roomService.deleteRoom(roomId);

        // then
        verify(roomRepository).deleteById(roomId);
    }

    @Test
    public void whenGetRoomsByHotelId_thenReturnRoomList() {
        // given
        Hotel hotel = new Hotel();
        hotel.setId(1L);
        Room room1 = new Room();
        room1.setHotel(hotel);
        Room room2 = new Room();
        room2.setHotel(hotel);
        List<Room> expectedRooms = Arrays.asList(room1, room2);

        when(roomRepository.findByHotelId(1L)).thenReturn(expectedRooms);

        // when
        List<Room> actualRooms = roomService.getRoomsByHotelId(1L);

        // then
        assertThat(actualRooms).isEqualTo(expectedRooms);
    }
}