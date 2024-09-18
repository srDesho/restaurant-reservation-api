package com.cristianml.reservation.mappers;

import com.cristianml.reservation.domain.entity.District;
import com.cristianml.reservation.dto.response.DistrictResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

    @Component
    @RequiredArgsConstructor
    public class DistrictMapper {

        private final ModelMapper modelMapper;

        // Method that converts an entity to dto
        public DistrictResponseDTO toResponseDTO(District district) {
            return modelMapper.map(district, DistrictResponseDTO.class);
        }
}
