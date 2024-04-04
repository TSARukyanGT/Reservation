package com.reservationservice.HotelReservationService.controllers;

import com.reservationservice.HotelReservationService.Reservation;
import com.reservationservice.HotelReservationService.repo.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.UUID;

@Controller
public class ReservationController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("title", "Бронирование");
        return "main";
    }

    @PostMapping("/")
    public String mainPost(@RequestParam String username, @RequestParam String hotelid,
                           @RequestParam Date startdate, @RequestParam Date enddate, Model model) {
        Reservation reservation = new Reservation(username, UUID.fromString(hotelid), startdate, enddate);
//        PostRepository.save(reservation);
        return "";
    }
}