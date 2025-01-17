package com.samuel.service;

import com.samuel.config.JwtService;
import com.samuel.dto.request.AuthenticationRequest;
import com.samuel.dto.response.AuthenticationResponse;
import com.samuel.exception.ApiRequest;
import com.samuel.repository.UserRepository;
import com.samuel.repository.projection.UserProfileProjection;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AuthenticationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	public Long UserId() {
		return getAuthenticatedUserId();
	}

	public UserProfileProjection getAuthenticatedUser() {
		return userRepository.findUserById(UserId(), UserProfileProjection.class)
				.orElseThrow(() -> new ApiRequest("USER_NOT_FOUND", HttpStatus.NOT_FOUND));
	}

	@Transactional
	public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getEmail(), authenticationRequest.getPassword()));

		if (!authentication.isAuthenticated())
			throw new ApiRequest("User Details cannot be Authenticated", HttpStatus.NOT_ACCEPTABLE);
		else {
			AuthenticationRequest authenticationRequest1 = userRepository.findByEmail(authenticationRequest.getEmail())
					.map(s -> AuthenticationRequest.builder().email(s.getEmail()).password(s.getPassword()).build())
					.get();
			String token = jwtService.generateToken(authenticationRequest);

			return AuthenticationResponse.builder().token(token).build();
		}
	}

	// RETRIEVES THE AUTHENTICATED USER ID FORM REQUEST HEADER
	private Long getAuthenticatedUserId() {
		RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) attribs).getRequest();
		String userId = request.getHeader("USER_ID_HEADER");

		if (userId == null)
			throw new ApiRequest("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
		else
			return Long.parseLong(userId);
	}
}
