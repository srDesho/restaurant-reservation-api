package com.cristianml.reservation.service;

import com.cristianml.reservation.domain.entity.Reservation;
import com.cristianml.reservation.dto.paypal.OrderCaptureResponse;
import com.cristianml.reservation.dto.paypal.OrderResponse;
import com.cristianml.reservation.dto.response.PaypalCaptureResponse;
import com.cristianml.reservation.dto.response.PaypalOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckoutService {

    private final PaypalService paypalService; // Handles interactions with the PayPal API.
    private final ReservationService reservationService; // Handles reservation-related operations.

    /**
     * Creates a PayPal payment URL for a specific reservation.
     * @param reservationId ID of the reservation.
     * @param returnUrl URL to redirect the user upon successful payment.
     * @param cancelUrl URL to redirect the user if the payment is canceled.
     * @return A response containing the PayPal approval URL.
     */
    public PaypalOrderResponse createPaypalPaymentUrl(Long reservationId, String returnUrl, String cancelUrl) {
        // Create a PayPal order for the reservation.
        OrderResponse orderResponse = this.paypalService.createOrder(
                reservationId,
                returnUrl,
                cancelUrl
        );

        // Extract the approval URL from the response.
        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve")) // Find the "approve" link.
                .findFirst()
                .orElseThrow(() -> new RuntimeException("")) // Handle missing link case.
                .getHref();

        return new PaypalOrderResponse(paypalUrl); // Return the approval URL wrapped in a response object.
    }

    /**
     * Captures a PayPal payment and updates the reservation status if successful.
     * @param orderId PayPal order ID to capture.
     * @return A response indicating whether the capture was successful and the reservation ID (if applicable).
     */
    public PaypalCaptureResponse capturePaypalPayment(String orderId) {
        // Capture the payment from PayPal.
        OrderCaptureResponse orderCaptureResponse = paypalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED"); // Check if the capture was successful.

        PaypalCaptureResponse paypalCaptureResponse = new PaypalCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if (completed) {
            // Retrieve the reservation ID from the PayPal purchase reference.
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().getFirst().getReferenceId();
            Reservation reservation = reservationService.confirmReservationPayment(Long.parseLong(purchaseIdStr)); // Confirm reservation payment.
            paypalCaptureResponse.setReservationId(reservation.getId());
        }
        return paypalCaptureResponse; // Return the capture response.
    }
}
