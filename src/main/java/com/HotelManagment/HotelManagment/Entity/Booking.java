package com.HotelManagment.HotelManagment.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name ="Bookings")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "check in date is needed")
    private LocalDate checkInDate;

    @Future(message = "check out date is needed when checkout")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "1 or more Adult Person should be")
    private int numberOfAdults;

    @Min(value = 0, message = "1 or more Adult Person should be")
    private int numberOfChildrens;

    private int totalNumberOfGuest;

    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

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

    public int getNumberOfChildrens() {
        return numberOfChildrens;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
        calculateGuest();
    }

    public void setNumberOfChildrens(int numberOfChildrens) {
        this.numberOfChildrens = numberOfChildrens;
        calculateGuest();
    }

    public void calculateGuest(){
        this.totalNumberOfGuest = this.numberOfAdults + this.numberOfChildrens;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "user=" + user +
                ", id=" + id +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfAdults=" + numberOfAdults +
                ", numberOfChildrens=" + numberOfChildrens +
                ", totalNumberOfGuest=" + totalNumberOfGuest +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +

                '}';
    }
}
