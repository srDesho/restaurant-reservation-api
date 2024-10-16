package com.cristianml.reservation.controller;

import com.cristianml.reservation.domain.entity.Reservation;
import com.cristianml.reservation.domain.entity.User;
import com.cristianml.reservation.dto.request.ReservationRequestDTO;
import com.cristianml.reservation.dto.response.ReservationResponseDTO;
import com.cristianml.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // Create a new reservation
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Validated ReservationRequestDTO request) {
        ReservationResponseDTO reservationResponseDTO = this.reservationService.createReservation(request);
        return new ResponseEntity<>(reservationResponseDTO, HttpStatus.CREATED);
    }

    // Get all reservations for the authenticated user
    @GetMapping("/my-reservations")
    public ResponseEntity<?> getAllReservationsByClient() {
        List<ReservationResponseDTO> reservations = this.reservationService.getReservationsByClientId();
        return ResponseEntity.ok(reservations);
    }

    // Get a specific reservation by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReservation(@PathVariable("id") Long id) {
        ReservationResponseDTO reservation = this.reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }
}