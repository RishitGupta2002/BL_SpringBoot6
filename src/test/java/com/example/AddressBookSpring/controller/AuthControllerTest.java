package com.example.AddressBookSpring.controller;

import com.example.AddressBookSpring.dto.UserInfoDto;
import com.example.AddressBookSpring.model.RefreshToken;
import com.example.AddressBookSpring.response.JwtResponseDTO;
import com.example.AddressBookSpring.service.JwtService;
import com.example.AddressBookSpring.service.RefreshTokenService;
import com.example.AddressBookSpring.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUp_Success() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUsername("testUser");

        // Create a RefreshToken object and set the token value
        RefreshToken refreshToken = RefreshToken.builder()
                .token("dummyRefreshToken") // Using the Builder pattern to set the token
                .build();

        when(userDetailsService.signupUser(userInfoDto)).thenReturn(true);
        when(refreshTokenService.createRefreshToken("testUser")).thenReturn(refreshToken);
        when(jwtService.GenerateToken("testUser")).thenReturn("dummyJwtToken");

        ResponseEntity<?> response = authController.SignUp(userInfoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponseDTO jwtResponseDTO = (JwtResponseDTO) response.getBody();
        assertEquals("dummyJwtToken", jwtResponseDTO.getAccessToken());
        assertEquals("dummyRefreshToken", jwtResponseDTO.getToken());

        verify(userDetailsService, times(1)).signupUser(userInfoDto);
        verify(refreshTokenService, times(1)).createRefreshToken("testUser");
        verify(jwtService, times(1)).GenerateToken("testUser");
    }

    @Test
    void testSignUp_AlreadyExists() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUsername("existingUser");

        when(userDetailsService.signupUser(userInfoDto)).thenReturn(false);

        ResponseEntity<?> response = authController.SignUp(userInfoDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Already Exist", response.getBody());

        verify(userDetailsService, times(1)).signupUser(userInfoDto);
        verifyNoInteractions(refreshTokenService);
        verifyNoInteractions(jwtService);
    }

    @Test
    void testSignUp_Exception() {
        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setUsername("testUser");

        when(userDetailsService.signupUser(userInfoDto)).thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<?> response = authController.SignUp(userInfoDto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Exception in User Service", response.getBody());

        verify(userDetailsService, times(1)).signupUser(userInfoDto);
        verifyNoInteractions(refreshTokenService);
        verifyNoInteractions(jwtService);
    }
}
