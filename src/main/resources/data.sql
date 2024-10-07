-- Limpiar datos existentes
DELETE FROM reservations;
DELETE FROM rooms;
DELETE FROM hotels;

-- Insertar Hoteles
INSERT INTO hotels (name) VALUES ('Hotel Sheraton');
INSERT INTO hotels (name) VALUES ('Hotel Hilton');
INSERT INTO hotels (name) VALUES ('Hotel Marriott');

-- Insertar Habitaciones
INSERT INTO rooms (room_number, hotel_id) VALUES ('101', 1);
INSERT INTO rooms (room_number, hotel_id) VALUES ('102', 1);
INSERT INTO rooms (room_number, hotel_id) VALUES ('103', 1);
INSERT INTO rooms (room_number, hotel_id) VALUES ('104', 1);
INSERT INTO rooms (room_number, hotel_id) VALUES ('105', 1);
INSERT INTO rooms (room_number, hotel_id) VALUES ('201', 2);
INSERT INTO rooms (room_number, hotel_id) VALUES ('202', 2);
INSERT INTO rooms (room_number, hotel_id) VALUES ('203', 2);
INSERT INTO rooms (room_number, hotel_id) VALUES ('204', 2);
INSERT INTO rooms (room_number, hotel_id) VALUES ('205', 2);
INSERT INTO rooms (room_number, hotel_id) VALUES ('301', 3);
INSERT INTO rooms (room_number, hotel_id) VALUES ('302', 3);
INSERT INTO rooms (room_number, hotel_id) VALUES ('303', 3);
INSERT INTO rooms (room_number, hotel_id) VALUES ('304', 3);
INSERT INTO rooms (room_number, hotel_id) VALUES ('305', 3);

-- Insertar Reservaciones
-- Hotel Sheraton
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-01', 'YYYY-MM-DD'), TO_DATE('2024-09-05', 'YYYY-MM-DD'), 1);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-10', 'YYYY-MM-DD'), TO_DATE('2024-09-15', 'YYYY-MM-DD'), 1);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-03', 'YYYY-MM-DD'), TO_DATE('2024-09-07', 'YYYY-MM-DD'), 2);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-20', 'YYYY-MM-DD'), TO_DATE('2024-09-25', 'YYYY-MM-DD'), 2);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-05', 'YYYY-MM-DD'), TO_DATE('2024-09-10', 'YYYY-MM-DD'), 3);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-15', 'YYYY-MM-DD'), TO_DATE('2024-09-20', 'YYYY-MM-DD'), 4);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-25', 'YYYY-MM-DD'), TO_DATE('2024-09-30', 'YYYY-MM-DD'), 5);

-- Hotel Hilton
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-02', 'YYYY-MM-DD'), TO_DATE('2024-09-06', 'YYYY-MM-DD'), 6);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-12', 'YYYY-MM-DD'), TO_DATE('2024-09-16', 'YYYY-MM-DD'), 6);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-07', 'YYYY-MM-DD'), TO_DATE('2024-09-11', 'YYYY-MM-DD'), 7);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-18', 'YYYY-MM-DD'), TO_DATE('2024-09-22', 'YYYY-MM-DD'), 7);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-09', 'YYYY-MM-DD'), TO_DATE('2024-09-14', 'YYYY-MM-DD'), 8);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-23', 'YYYY-MM-DD'), TO_DATE('2024-09-28', 'YYYY-MM-DD'), 9);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-01', 'YYYY-MM-DD'), TO_DATE('2024-09-05', 'YYYY-MM-DD'), 10);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-20', 'YYYY-MM-DD'), TO_DATE('2024-09-25', 'YYYY-MM-DD'), 10);

-- Hotel Marriott
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-04', 'YYYY-MM-DD'), TO_DATE('2024-09-08', 'YYYY-MM-DD'), 11);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-14', 'YYYY-MM-DD'), TO_DATE('2024-09-18', 'YYYY-MM-DD'), 11);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-06', 'YYYY-MM-DD'), TO_DATE('2024-09-10', 'YYYY-MM-DD'), 12);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-21', 'YYYY-MM-DD'), TO_DATE('2024-09-25', 'YYYY-MM-DD'), 12);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-11', 'YYYY-MM-DD'), TO_DATE('2024-09-15', 'YYYY-MM-DD'), 13);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-26', 'YYYY-MM-DD'), TO_DATE('2024-09-30', 'YYYY-MM-DD'), 13);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-08', 'YYYY-MM-DD'), TO_DATE('2024-09-13', 'YYYY-MM-DD'), 14);
INSERT INTO reservations (check_in_date, check_out_date, room_id) VALUES (TO_DATE('2024-09-19', 'YYYY-MM-DD'), TO_DATE('2024-09-24', 'YYYY-MM-DD'), 15);