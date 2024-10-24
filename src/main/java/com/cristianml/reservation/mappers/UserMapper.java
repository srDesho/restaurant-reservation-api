package com.cristianml.reservation.mappers;

import com.cristianml.reservation.domain.entity.User;
import com.cristianml.reservation.dto.request.SignupRequestDTO;
import com.cristianml.reservation.dto.response.AuthResponseDTO;
import com.cristianml.reservation.dto.response.UserProfileResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toUser(SignupRequestDTO signupRequestDTO) {
        return modelMapper.map(signupRequestDTO, User.class);
    }

    public UserProfileResponseDTO toUserProfileResponseDTO(User user) {
        return modelMapper.map(user, UserProfileResponseDTO.class);
    }

    public AuthResponseDTO toAuthResponseDTO(String token, UserProfileResponseDTO userProfile) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken(token);
        authResponseDTO.setUser(userProfile);
        return authResponseDTO;
    }
}
