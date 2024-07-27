package com.dev.demo_spring_boot_sql.service;

import com.dev.demo_spring_boot_sql.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice);

    List<String> getRoomTypes();

    List<Room> gettAllRooms();

    byte[] getRoomPhotoByRoomId(Long roomId);

    void deleteRoom(Long roomId);

    Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes);

    Optional<Room> getRoomById(Long roomId);

    List<Room> getALlAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
}
