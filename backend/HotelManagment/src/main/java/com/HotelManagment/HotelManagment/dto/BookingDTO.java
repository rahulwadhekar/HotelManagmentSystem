package com.HotelManagment.HotelManagment.dto;

import com.HotelManagment.HotelManagment.Entity.Room;
import com.HotelManagment.HotelManagment.Entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {


    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private int numberOfAdults;

    private int numberOfChildrens;

    private int totalNumberOfGuest;

    private String bookingConfirmationCode;

    private UserDTO user;

    private RoomDTO room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildrens() {
        return numberOfChildrens;
    }

    public void setNumberOfChildrens(int numberOfChildrens) {
        this.numberOfChildrens = numberOfChildrens;
    }

    public int getTotalNumberOfGuest() {
        return totalNumberOfGuest;
    }

    public void setTotalNumberOfGuest(int totalNumberOfGuest) {
        this.totalNumberOfGuest = totalNumberOfGuest;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public RoomDTO getRoom() {
        return room;
    }

    public void setRoom(RoomDTO room) {
        this.room = room;
    }
}
