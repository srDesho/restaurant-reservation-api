package com.cristianml.reservation.service;

import com.cristianml.reservation.domain.entity.District;
import com.cristianml.reservation.dto.response.DistrictResponseDTO;
import com.cristianml.reservation.mappers.DistrictMapper;
import com.cristianml.reservation.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    // Method to get all districts
    @Transactional(readOnly = true)
    public List<DistrictResponseDTO> getAllDistricts() {
        List<District> districtList = this.districtRepository.findAll();
        return districtMapper.toResponseDTOList(districtList); // We mapped the list
    }

}
