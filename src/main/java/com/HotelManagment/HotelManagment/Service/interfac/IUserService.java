package com.HotelManagment.HotelManagment.Service.interfac;

import com.HotelManagment.HotelManagment.Entity.User;
import com.HotelManagment.HotelManagment.dto.Request;
import com.HotelManagment.HotelManagment.dto.Responce;

public interface IUserService {

    Responce register(User request);
    Responce login(Request request);
    Responce getAllusers();
    Responce getUserBookingHistory(String userId);
    Responce deleteUser(String userId);
    Responce getuserById(String userId);
    Responce getMyInfo(String email);
}
