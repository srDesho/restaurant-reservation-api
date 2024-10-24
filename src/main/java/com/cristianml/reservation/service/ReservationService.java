package com.cristianml.reservation.service;

import com.cristianml.reservation.domain.entity.Reservation;
import com.cristianml.reservation.domain.entity.Restaurant;
import com.cristianml.reservation.domain.entity.User;
import com.cristianml.reservation.domain.enums.ReservationStatus;
import com.cristianml.reservation.dto.request.ReservationRequestDTO;
import com.cristianml.reservation.dto.response.ReservationResponseDTO;
import com.cristianml.reservation.exception.ResourceNotFoundException;
import com.cristianml.reservation.mappers.ReservationMapper;
import com.cristianml.reservation.repository.ReservationRepository;
import com.cristianml.reservation.repository.RestaurantRepository;
import com.cristianml.reservation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final ReservationMapper reservationMapper;

    // Creates a new reservation for a client and saves it in the database.
    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO reservationRequestDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = this.userRepository.findOneByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found by username."));

        Restaurant restaurant = this.restaurantRepository.findById(reservationRequestDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found by Id."));

        Reservation reservation = reservationMapper.toEntity(reservationRequestDTO);
        reservation.setRestaurant(restaurant);
        reservation.setClient(user);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.calculateTotalAmount();

        reservation = reservationRepository.save(reservation);
        return reservationMapper.toResponseDto(reservation);
    }

    // Retrieves all reservations made by the currently authenticated client.
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> getReservationsByClientId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findOneByEmail(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        List<Reservation> reservations = reservationRepository.findByClientId(user.getId());
        return reservationMapper.toResponseDtoList(reservations);
    }

    // Retrieves a reservation by its ID.
    @Transactional(readOnly = true)
    public ReservationResponseDTO getReservationById(Long reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found."));

        return reservationMapper.toResponseDto(reservation);
    }

    // Updates the status of a reservation to "PAID" after confirming payment.
    @Transactional
    public Reservation confirmReservationPayment(Long reservationId) {
        Reservation reservation = this.reservationRepository.findById(reservationId)
                .orElseThrow(ResourceNotFoundException::new);
        reservation.setStatus(ReservationStatus.PAID);
        return reservationRepository.save(reservation);
    }
}
