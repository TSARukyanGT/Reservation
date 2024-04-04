package com.reservationservice.HotelReservationService;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import java.sql.Date;
import java.util.UUID;


@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID reservationUid;

    private String username;
    private UUID hotelUid;
    private String resStatus;
    private Date startDate;
    private Date endDate;

    public UUID getReservationUid() {
        return reservationUid;
    }

    public void setReservationUid(UUID reservationUid) {
        this.reservationUid = reservationUid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(UUID hotelUid) {
        this.hotelUid = hotelUid;
    }

    public String getResStatus() {
        return resStatus;
    }

    public void setResStatus(String resStatus) {
        this.resStatus = resStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "reservationUid=" + reservationUid +
                ", username='" + username + '\'' +
                ", hotelUid=" + hotelUid +
                ", resStatus='" + resStatus + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public Reservation() {
    }

    public Reservation(String username, UUID hotelUid, Date startDate, Date endDate) {
        this.username = username;
        this.hotelUid = hotelUid;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
