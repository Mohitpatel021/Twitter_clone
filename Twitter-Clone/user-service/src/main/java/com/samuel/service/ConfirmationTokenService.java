package com.samuel.service;

import com.samuel.model.ConfirmationToken;
import com.samuel.model.User;
import com.samuel.repository.ConfirmationTokenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService {
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
	private UserConfirmationService userConfirmationService;

	@Transactional
	public String createConfirmationToken(User user) {
		String token = UUID.randomUUID().toString();

		ConfirmationToken confirmationToken = ConfirmationToken.builder().token(token).createdAt(LocalDateTime.now())
				.expiresAt(LocalDateTime.now().plusMinutes(15)).user(user).build();
		confirmationTokenRepository.save(confirmationToken);
		return token;
	}

	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Verification Token does not exists"));

		if (confirmationToken.getConfirmedAt() != null)
			throw new RuntimeException("Token is already confirmed");

		if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
			throw new RuntimeException("Token is already expired");

		updateTokenConfirmedAt(confirmationToken.getToken());

		userConfirmationService.ConfirmUserEmail(confirmationToken.getUser());

		return "You have been successfully registered";
	}

	@Transactional
	public void updateTokenConfirmedAt(String token) {
		confirmationTokenRepository.updateConfirmedAt(token, String.valueOf(LocalDateTime.now()));
	}

	public User getUserByToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
				.orElseThrow(() -> new RuntimeException("Verification Token does not exists"));

		return confirmationToken.getUser();
	}
}
