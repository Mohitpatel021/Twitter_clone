package com.samuel.service;

import com.samuel.model.User;
import com.samuel.model.UserConfirmation;
import com.samuel.repository.UserConfirmationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserConfirmationService {
	@Autowired
	private UserConfirmationRepository userConfirmationRepository;

	@Transactional
	public void ConfirmUserEmail(User user) {

		UserConfirmation userConfirmation = UserConfirmation.builder().user(user).emailConfirmed(true).build();
		userConfirmationRepository.save(userConfirmation);
	}
}
