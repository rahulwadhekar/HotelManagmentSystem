package com.HotelManagment.HotelManagment.Service.interfac;

import com.HotelManagment.HotelManagment.Entity.User;
import com.HotelManagment.HotelManagment.dto.Responce;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IRoomService {

    Responce addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description);

    List<String> getAllRoomTypes();

    Responce getAllRooms();

    Responce deleteRoom(Long roomId);

    Responce updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo);

    Responce getRoomById(Long roomId);

    Responce getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

    Responce getAllAvailableRooms();

}
