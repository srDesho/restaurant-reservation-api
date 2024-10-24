package com.cristianml.reservation.service;

import com.cristianml.reservation.domain.entity.Restaurant;
import com.cristianml.reservation.dto.response.RestaurantResponseDTO;
import com.cristianml.reservation.exception.ResourceNotFoundException;
import com.cristianml.reservation.mappers.RestaurantMapper;
import com.cristianml.reservation.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for managing restaurant operations, including retrieval,
 * pagination, and mapping of entities to DTOs.
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper; // Ensure this is injected properly

    /**
     * Retrieves a paginated list of all restaurants.
     * @param pageable pagination details (e.g., page number, size, sorting)
     * @return a paginated list of RestaurantResponseDTO objects
     */
    @Transactional(readOnly = true)
    public Page<RestaurantResponseDTO> getAllRestaurants(Pageable pageable) {
        Page<Restaurant> restaurants = this.restaurantRepository.findAll(pageable);
        return restaurants.map(restaurantMapper::toResponseDTO);
    }

    /**
     * Retrieves a paginated list of restaurants filtered by district name.
     * @param districtName the name of the district to filter by
     * @param pageable pagination details (e.g., page number, size, sorting)
     * @return a paginated list of RestaurantResponseDTO objects
     */
    @Transactional(readOnly = true)
    public Page<RestaurantResponseDTO> getAllRestaurantByDistrictName(String districtName, Pageable pageable) {
        Page<Restaurant> restaurants = this.restaurantRepository.findByDistrictName(districtName, pageable);
        return restaurants.map(restaurantMapper::toResponseDTO);
    }

    /**
     * Retrieves a restaurant by its ID.
     * @param id the ID of the restaurant
     * @return a RestaurantResponseDTO object containing restaurant details
     * @throws ResourceNotFoundException if no restaurant is found with the given ID
     */
    @Transactional(readOnly = true)
    public RestaurantResponseDTO findById(Long id) {
        Restaurant restaurant = this.restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
        return restaurantMapper.toResponseDTO(restaurant);
    }
}
