package com.cristianml.reservation.controller;

import com.cristianml.reservation.dto.request.AuthRequestDTO;
import com.cristianml.reservation.dto.request.SignupRequestDTO;
import com.cristianml.reservation.dto.response.AuthResponseDTO;
import com.cristianml.reservation.dto.response.UserProfileResponseDTO;
import com.cristianml.reservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO authResponse = userService.signIn(authRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserProfileResponseDTO> register(@RequestBody @Validated SignupRequestDTO signupRequestDTO) {
        UserProfileResponseDTO userProfileResponseDTO = userService.signup(signupRequestDTO);
        return new ResponseEntity<>(userProfileResponseDTO, HttpStatus.CREATED);
    }

}
