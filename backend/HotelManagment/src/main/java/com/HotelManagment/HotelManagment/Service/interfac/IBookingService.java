package com.HotelManagment.HotelManagment.Service.interfac;

import com.HotelManagment.HotelManagment.Entity.Booking;
import com.HotelManagment.HotelManagment.dto.Responce;

public interface IBookingService {
    Responce saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Responce findBookingByConfirmationCode(String confirmationCode);

    Responce getAllBookings();

    Responce cancelBooking(Long bookingId);
}
