package com.HotelManagment.HotelManagment.Service.implimentation;

import com.HotelManagment.HotelManagment.Entity.Room;
import com.HotelManagment.HotelManagment.Exceptions.OurException;
import com.HotelManagment.HotelManagment.Repository.BookingRepository;
import com.HotelManagment.HotelManagment.Repository.RoomRepository;
import com.HotelManagment.HotelManagment.Service.AwsS3Service;
import com.HotelManagment.HotelManagment.Service.interfac.IRoomService;
import com.HotelManagment.HotelManagment.dto.Responce;
import com.HotelManagment.HotelManagment.dto.RoomDTO;
import com.HotelManagment.HotelManagment.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service

public class RoomService implements IRoomService {

    @Autowired
   private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private AwsS3Service awsS3Service;

    @Override
    public Responce addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Responce response = new Responce();

        try {
            String imageUrl = awsS3Service.saveImageFile(photo);
            Room room = new Room();
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoom(roomDTO);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }


    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Responce getAllRooms() {
        Responce responce = new Responce();

        try{
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setRoomList(roomDTOList);
        }catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error saving a room " + e.getMessage());
        }
        return responce;
    }

    @Override
    public Responce deleteRoom(Long roomId) {

        Responce responce = new Responce();
        try{
            roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            roomRepository.deleteById(roomId);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setStatusCode(200);
            responce.setMessage("successful");
        }catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error saving a room " + e.getMessage());
        }
        return responce;


    }

    @Override
    public Responce updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Responce responce = new Responce();
        try {
            String imageUrl = null;
            if (photo != null && !photo.isEmpty()) {
                imageUrl = awsS3Service.saveImageFile(photo);
            }
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            if (roomType != null) room.setRoomType(roomType);
            if (roomPrice != null) room.setRoomPrice(roomPrice);
            if (description != null) room.setRoomDescription(description);
            if (imageUrl != null) room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTO(updatedRoom);

            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setRoom(roomDTO);

        } catch (OurException e) {
            responce.setStatusCode(404);
            responce.setMessage(e.getMessage());
        } catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error saving a room " + e.getMessage());
        }
        return responce;

    }

    @Override
    public Responce getRoomById(Long roomId) {
        Responce responce = new Responce();

        try {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new OurException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEntityToRoomDTOPlusBookings(room);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setRoom(roomDTO);

        } catch (OurException e) {
            responce.setStatusCode(404);
            responce.setMessage(e.getMessage());
        } catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error saving a room " + e.getMessage());
        }
        return responce;

    }

    @Override
    public Responce getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {

        Responce responce = new Responce();


        try {
            List<Room> availableRooms = roomRepository.findAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(availableRooms);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setRoomList(roomDTOList);

        } catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error saving a room " + e.getMessage());
        }
        return responce;
    }

    @Override
    public Responce getAllAvailableRooms() {
        Responce response = new Responce();

        try {
            List<Room> roomList = roomRepository.getAllAvailableRooms();
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setRoomList(roomDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error saving a room " + e.getMessage());
        }
        return response;
    }
}
