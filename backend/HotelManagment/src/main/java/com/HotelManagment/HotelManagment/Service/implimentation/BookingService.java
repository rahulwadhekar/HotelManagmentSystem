package com.HotelManagment.HotelManagment.Service.implimentation;

import com.HotelManagment.HotelManagment.Entity.Booking;
import com.HotelManagment.HotelManagment.Entity.Room;
import com.HotelManagment.HotelManagment.Entity.User;
import com.HotelManagment.HotelManagment.Exceptions.OurException;
import com.HotelManagment.HotelManagment.Repository.BookingRepository;
import com.HotelManagment.HotelManagment.Repository.RoomRepository;
import com.HotelManagment.HotelManagment.Repository.UserRepository;
import com.HotelManagment.HotelManagment.Service.interfac.IBookingService;
import com.HotelManagment.HotelManagment.Service.interfac.IRoomService;
import com.HotelManagment.HotelManagment.dto.BookingDTO;
import com.HotelManagment.HotelManagment.dto.Responce;
import com.HotelManagment.HotelManagment.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BookingService implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public Responce saveBooking(Long roomId, Long userId, Booking bookingRequest) {

        Responce responce = new Responce();

        try{
            if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
                throw new IllegalAccessException("check in date must be correct");
            }
            Room room = roomRepository.findById(roomId).orElseThrow(()-> new OurException("Room not Found"));
            User user = userRepository.findById(userId).orElseThrow(()-> new OurException("User Not Found"));

            List<Booking> existingBooking = room.getBookings();

            if(!roomIsAvailable(bookingRequest,existingBooking)){
                throw  new OurException("Room Not Availble for selected Date Ronge");

            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmationCode = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
            bookingRepository.save(bookingRequest);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setBookingConfirmationCode(bookingConfirmationCode);

        } catch (OurException e) {
            responce.setStatusCode(404);
            responce.setMessage(e.getMessage());
        } catch (IllegalAccessException e) {
            responce.setStatusCode(500);
            responce.setMessage("Error Saving a booking: " + e.getMessage());
        }

        return responce;
    }

    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }

    @Override
    public Responce findBookingByConfirmationCode(String confirmationCode) {
        Responce response = new Responce();
        try {
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(() -> new OurException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking, true);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setBooking(bookingDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error Finding a booking: " + e.getMessage());

        }
        return response;
    }

    @Override
    public Responce getAllBookings() {

        Responce responce = new Responce();

        try {
            List<Booking> bookingList = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingListDTO(bookingList);
            responce.setStatusCode(200);
            responce.setMessage("successful");
            responce.setBookingList(bookingDTOList);

        } catch (OurException e) {
            responce.setStatusCode(404);
            responce.setMessage(e.getMessage());

        } catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error Getting all bookings: " + e.getMessage());

        }
        return responce;
    }

    @Override
    public Responce cancelBooking(Long bookingId) {

        Responce responce = new Responce();

        try {
            bookingRepository.findById(bookingId).orElseThrow(() -> new OurException("Booking Does Not Exist"));
            bookingRepository.deleteById(bookingId);
            responce.setStatusCode(200);
            responce.setMessage("successful");

        } catch (OurException e) {
            responce.setStatusCode(404);
            responce.setMessage(e.getMessage());

        } catch (Exception e) {
            responce.setStatusCode(500);
            responce.setMessage("Error Cancelling a booking: " + e.getMessage());

        }
        return responce;
    }
}
