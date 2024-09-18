package com.cristianml.reservation.mappers;

import com.cristianml.reservation.domain.entity.Restaurant;
import com.cristianml.reservation.dto.response.RestaurantResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {

    private final ModelMapper modelMapper;

    // Method that converts an entity to dto
    public RestaurantResponseDTO toResponseDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }
}
