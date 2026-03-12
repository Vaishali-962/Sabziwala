package com.velocity.sabziwala.service;

import java.time.Instant;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.velocity.sabziwala.dto.request.LoginRequest;
import com.velocity.sabziwala.dto.request.RefreshTokenRequest;
import com.velocity.sabziwala.dto.request.RegisterRequest;
import com.velocity.sabziwala.dto.response.AuthResponse;
import com.velocity.sabziwala.dto.response.UserResponse;
import com.velocity.sabziwala.entity.RefreshToken;
import com.velocity.sabziwala.entity.User;
import com.velocity.sabziwala.exception.DuplicateException;
import com.velocity.sabziwala.repository.RefreshTokenRepository;
import com.velocity.sabziwala.repository.UserRepository;
import com.velocity.sabziwala.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	//Register Customer
	public AuthResponse register(@Valid RegisterRequest request) {
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateException("Email already exists for another customer");
		}
		
		if(userRepository.existsByUserName(request.getUserName())) {
			throw new DuplicateException("Username already exists for another customer");
		}
		
		User user = User.builder()
				.userName(request.getUserName())
				.email(request.getEmail())
				.fullName(request.getFullName())
				.passwordHash(passwordEncoder.encode(request.getPassword()))
				.phone(request.getPhone())
				.locality(request.getLocality())
				.build();
		
		User savedUser = userRepository.save(user);
		return generateAuthResponse(savedUser);
	}

	public AuthResponse login(@Valid LoginRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public AuthResponse refreshToken(@Valid RefreshTokenRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public void logout(String accessToken, String username) {
		// TODO Auto-generated method stub
		
	}
	
	private AuthResponse generateAuthResponse(User user) {
		// Generate Access Token
		String accessToken = jwtService.generateAccessToken(user.getUserId(), user.getEmail(), user.getRole().name(), user.getUserName());
		
		// Refresh token
		String refreshToken = jwtService.generateRefreshToken();
		
		// Persist refresh token to DB
		RefreshToken rToken = new RefreshToken();
		rToken.setUser(user);
		rToken.setToken(refreshToken);
		rToken.setExpiryDate(Instant.now().plusMillis(85000));
		rToken.setIsRevoked(false);
		
		refreshTokenRepository.save(rToken);
		
		return AuthResponse.of(accessToken, refreshToken, jwtService.getAccessTokenExpirationSeconds(), UserResponse.from(user));
		
	}

}
