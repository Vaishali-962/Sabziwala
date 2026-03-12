package com.velocity.sabziwala.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.velocity.sabziwala.entity.User;
import com.velocity.sabziwala.enums.AuthProvider;
import com.velocity.sabziwala.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	private UUID userId;
	private String userName;
	private String email;
	private String fullName;
	private String phone;
	private String locality;
	private String address;
	private Role role;
	private AuthProvider authProvider;
	private Boolean emailVerified;
	private LocalDateTime createdAt;

	public static UserResponse from(User user) {

		return UserResponse.builder().userId(user.getUserId()).userName(user.getUserName()).email(user.getEmail())
				.fullName(user.getFullName()).phone(user.getPhone()).locality(user.getLocality())
				.address(user.getAddress()).role(user.getRole()).authProvider(user.getAuthProvider())
				.emailVerified(user.getEmailVerified()).createdAt(user.getCreatedAt()).build();
	}
}
