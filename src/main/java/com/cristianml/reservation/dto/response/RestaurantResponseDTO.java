package com.cristianml.reservation.dto.response;

import lombok.Data;

@Data
public class RestaurantResponseDTO {

    // It is not necessary for a DTO to have the same attributes as the entity.
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private String districtName;
    private double pricePerPerson;
    private int capacity;

}
