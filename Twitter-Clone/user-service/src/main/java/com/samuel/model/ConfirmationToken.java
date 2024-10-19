package com.samuel.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_generator")
	@SequenceGenerator(sequenceName = "confirmation_token_seq", name = "confirmation_token_generator", allocationSize = 1, initialValue = 1)
	private Long id;

	private String token;

	private LocalDateTime createdAt;

	private LocalDateTime expiresAt;

	private LocalDateTime ConfirmedAt;

	@OneToOne
	@JoinColumn(name = "User_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(LocalDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public LocalDateTime getConfirmedAt() {
		return ConfirmedAt;
	}

	public void setConfirmedAt(LocalDateTime confirmedAt) {
		ConfirmedAt = confirmedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
