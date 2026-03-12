package com.velocity.sabziwala.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.velocity.sabziwala.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
	

}
