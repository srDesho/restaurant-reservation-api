package com.cristianml.reservation.controller;

import com.cristianml.reservation.dto.response.PaypalCaptureResponse;
import com.cristianml.reservation.dto.response.PaypalOrderResponse;
import com.cristianml.reservation.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Endpoint to create a PayPal payment URL for a reservation.
     *
     * @param reservationId The ID of the reservation.
     * @param returnUrl URL to redirect after successful payment.
     * @param cancelUrl URL to redirect if payment is canceled.
     * @return A response containing the PayPal approval URL.
     */
    @PostMapping("/paypal/create")
    public PaypalOrderResponse createPaypalOrder(
            @RequestParam Long reservationId,
            @RequestParam String returnUrl,
            @RequestParam String cancelUrl) {

        return checkoutService.createPaypalPaymentUrl(reservationId, returnUrl, cancelUrl);
    }

    /**
     * Endpoint to capture a PayPal payment after approval.
     *
     * @param orderId The ID of the PayPal order to capture.
     * @return A response indicating if the payment was completed and related reservation info.
     */
    @PostMapping("/paypal/capture")
    public PaypalCaptureResponse capturePaypalOrder(
            @RequestParam String orderId) {
        return checkoutService.capturePaypalPayment(orderId);
    }

}
