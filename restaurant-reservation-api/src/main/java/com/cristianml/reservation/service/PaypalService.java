package com.cristianml.reservation.service;

import com.cristianml.reservation.domain.entity.Reservation;
import com.cristianml.reservation.dto.paypal.*;
import com.cristianml.reservation.exception.ResourceNotFoundException;
import com.cristianml.reservation.repository.ReservationRepository;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Base64;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PaypalService {

    // Client ID for PayPal API authentication, injected from application properties
    @Value("${paypal.client-id}")
    private String clientId;

    // Client Secret for PayPal API authentication, injected from application properties
    @Value("${paypal.client-secret}")
    private String clientSecret;

    // Base URL for the PayPal API (sandbox or production), injected from application properties
    @Value("${paypal.api-base}")
    private String apiBase;

    // Repository to access reservation data
    @NonNull
    private ReservationRepository reservationRepository;

    /**
     * RestClient: A modern HTTP client introduced in Spring 6, used here to interact
     * with the PayPal API. It simplifies HTTP requests while supporting authentication,
     * headers, and serialization/deserialization of objects.
     */
    // RestClient instance for making HTTP requests to the PayPal API
    private RestClient paypalClient;

    // Initialize RestClient with the PayPal API base URL
    @PostConstruct
    public void init() {
        paypalClient = RestClient
                .builder()
                .baseUrl(apiBase)
                .build();
    }

    /**
     * Obtains an access token from the PayPal API for authentication purposes.
     * Uses client credentials to generate a token.
     */
    private String getAccessToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        return Objects.requireNonNull(paypalClient.post()
                        .uri("/v1/oauth2/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " +
                                Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                        .body(body)
                        .retrieve()
                        .toEntity(TokenResponse.class)
                        .getBody())
                .getAccessToken();
    }

    /**
     * Creates a PayPal order for a reservation.
     * Sends the total amount, return URL, and cancel URL to PayPal for checkout setup.
     */
    public OrderResponse createOrder(Long reservationId, String returnUrl, String cancelUrl) {
        Reservation reservation = reservationRepository
                .findById(reservationId)
                .orElseThrow(ResourceNotFoundException::new);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIntent("CAPTURE");

        // Set the amount details for the order
        Amount amount = new Amount();
        amount.setCurrencyCode("USD");
        amount.setValue(reservation.getTotalAmount().toString());

        // Add purchase details, including the reservation reference
        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setReferenceId(reservation.getId().toString());
        purchaseUnit.setAmount(amount);

        orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));

        // Configure application context for the order (e.g., branding, redirect URLs)
        ApplicationContext applicationContext = ApplicationContext
                .builder()
                .brandName("CmlReserve")
                .returnURL(returnUrl)
                .cancelURL(cancelUrl)
                .build();

        orderRequest.setApplicationContext(applicationContext);

        // Send the order request to PayPal and return the response
        return paypalClient.post()
                .uri("/v2/checkout/orders")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .body(orderRequest)
                .retrieve()
                .toEntity(OrderResponse.class)
                .getBody();
    }

    /**
     * Captures payment for a PayPal order using the order ID.
     * Marks the transaction as completed.
     */
    public OrderCaptureResponse captureOrder(String orderId) {
        return paypalClient.post()
                .uri("v2/checkout/orders/{order_id}/capture", orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(OrderCaptureResponse.class)
                .getBody();
    }
}