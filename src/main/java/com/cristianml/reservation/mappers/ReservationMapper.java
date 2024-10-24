package com.cristianml.reservation.mappers;

import com.cristianml.reservation.domain.entity.Reservation;
import com.cristianml.reservation.dto.request.ReservationRequestDTO;
import com.cristianml.reservation.dto.response.ReservationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ReservationMapper {

    private final ModelMapper modelMapper;

    public Reservation toEntity(ReservationRequestDTO reservationRequestDTO) {
        return modelMapper.map(reservationRequestDTO, Reservation.class);
    }

    public ReservationResponseDTO toResponseDto(Reservation reservation) {
        return modelMapper.map(reservation, ReservationResponseDTO.class);
    }


    public List<ReservationResponseDTO> toResponseDtoList(List<Reservation> reservations) {
        return reservations.stream()
                .map(this::toResponseDto)
                .toList();
    }
}

