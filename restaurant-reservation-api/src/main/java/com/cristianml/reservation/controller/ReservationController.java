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

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Validated ReservationRequestDTO request) {
        ReservationResponseDTO reservationResponseDTO = this.reservationService.createReservation(request);
        return new ResponseEntity<>(reservationResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllReservationsByClient() {
        List<ReservationResponseDTO> reservations = this.reservationService.getReservationsByClientId();
        return ResponseEntity.ok(reservations);
    }

}
