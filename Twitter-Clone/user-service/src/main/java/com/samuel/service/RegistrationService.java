package com.samuel.service;

import com.samuel.dto.request.PasswordRegistrationRequest;
import com.samuel.dto.request.RegistrationRequest;
import com.samuel.enums.Role;
import com.samuel.exception.ApiRequest;
import com.samuel.model.User;
import com.samuel.model.UserMetadata;
import com.samuel.repository.UserMetadataRepository;
import com.samuel.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserMetadataRepository userMetadataRepository;
	@Autowired
	private IsEmailValid isemailValid;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public String registration(RegistrationRequest registrationRequest, HttpServletRequest httpServletRequest) {

		String UserLastname = getLastnameFromFullname(registrationRequest.fullName());

		Optional<User> userOptional = userRepository.findByEmail(registrationRequest.email());

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			String token = confirmationTokenService.createConfirmationToken(user);
			String url = applicationUrl(httpServletRequest) + "/API/V1/AUTH/REGISTRATION/confirm?token=" + token;

			if (!user.getUserConfirmation().isEmailConfirmed()) {
				emailService.send(user.getEmail(), emailService.buildEmail(UserLastname, url));
				return "User {}" + user.getFullname()
						+ " account has not been activated, please go to email to confirm your registration";
			}
			throw new ApiRequest("USER ALREADY EXITS", HttpStatus.CONFLICT);
		}

		boolean isEmailValid = isemailValid.test(registrationRequest.email());
		if (!isEmailValid)
			throw new ApiRequest("EMAIL INVALID", HttpStatus.CONFLICT);

		User user = User.builder().fullname(registrationRequest.fullName()).email(registrationRequest.email())
				.role(Role.USER).registrationDate(LocalDateTime.now()).build();
		userRepository.saveAndFlush(user);

		UserMetadata userMetadata = UserMetadata.builder().user(user).birthDate(registrationRequest.dateOfBirth())
				.build();
		userMetadataRepository.save(userMetadata);

		String token = confirmationTokenService.createConfirmationToken(user);
		String url = applicationUrl(httpServletRequest) + "/API/V1/AUTH/REGISTRATION/confirm?token=" + token;
		emailService.send(user.getEmail(), emailService.buildEmail(UserLastname, url));

		return token;
		// return "Please Check your Email to confirm your registration";
	}

	@Transactional
	public String passwordRegistration(String token, PasswordRegistrationRequest passwordRegistrationRequest) {
		if (passwordRegistrationRequest.password().length() < 5)
			throw new ApiRequest("PASSWORD TO SHORT", HttpStatus.CONFLICT);

		Long userId = confirmationTokenService.getUserByToken(token).getUserId();
		userRepository.updatePasswordById(userId, passwordEncoder.encode(passwordRegistrationRequest.password()));
		return "Password Updated Successfully";
	}

	// FILTERS THE USER FULL-NAME AND GETS THE LASTNAME ONLY
	private String getLastnameFromFullname(String fullName) {
		String trimmedFullname = fullName.trim();
		return trimmedFullname.substring(trimmedFullname.lastIndexOf(" ") + 1);
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}