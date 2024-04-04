package com.reservationservice.HotelReservationService.repo;

import com.reservationservice.HotelReservationService.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PostRepository extends CrudRepository<Reservation, UUID> {
}
