package com.cristianml.reservation.controller;

import com.cristianml.reservation.dto.response.RestaurantResponseDTO;
import com.cristianml.reservation.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    // Dependency injection for the service layer
    private final RestaurantService restaurantService;

    /**
     * Get a paginated list of all restaurants.
     * @param pageable Contains pagination details such as page number and size.
     * @return ResponseEntity with a paginated list of RestaurantResponseDTO.
     */
    @GetMapping("/page")
    public ResponseEntity<Page<RestaurantResponseDTO>> getAllRestaurants(@PageableDefault(size = 5) Pageable pageable) {
        Page<RestaurantResponseDTO> restaurants = this.restaurantService.getAllRestaurants(pageable);
        return ResponseEntity.ok(restaurants); // Return HTTP 200 with the paginated result.
    }

    /**
     * Get a paginated list of restaurants filtered by district name.
     * @param pageable Contains pagination details such as page number and size.
     * @param name The name of the district to filter restaurants.
     * @return ResponseEntity with a paginated list of RestaurantResponseDTO filtered by district.
     */
    @GetMapping("/page/district")
    public ResponseEntity<Page<RestaurantResponseDTO>> findByDistrict(@PageableDefault(size = 5) Pageable pageable,
                                                                      @RequestParam String name) {
        Page<RestaurantResponseDTO> restaurants = this.restaurantService.getAllRestaurantByDistrictName(name, pageable);
        return ResponseEntity.ok(restaurants); // Return HTTP 200 with filtered paginated results.
    }

    /**
     * Get a specific restaurant by its ID.
     * @param id The unique identifier of the restaurant.
     * @return ResponseEntity with the details of the restaurant if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDTO> findById(@PathVariable("id") Long id) {
        RestaurantResponseDTO restaurantResponseDTO = this.restaurantService.findById(id);
        return ResponseEntity.ok(restaurantResponseDTO); // Return HTTP 200 with restaurant details.
    }
}

