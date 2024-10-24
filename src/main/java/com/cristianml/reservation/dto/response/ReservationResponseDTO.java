package com.cristianml.reservation.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

// Represents the response data for a reservation, including details like restaurant name,
// reservation date, number of people, status, additional info, and total amount.

@Data
public class ReservationResponseDTO {
    private Long id;
    private String restaurantName;
    private LocalDateTime reservationDate;
    private int numberOfPeople;
    private String status;
    private String additionalInfo;
    private double totalAmount;
}
