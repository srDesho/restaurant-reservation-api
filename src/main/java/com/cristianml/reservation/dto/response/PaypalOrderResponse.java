package com.cristianml.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaypalOrderResponse {

    private String paypalUrl;

}
