package com.cristianml.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PaypalCaptureResponse {

    private boolean completed;
    private Long reservationId;

}
