package com.samuel.config;

import com.samuel.dto.request.AuthenticationRequest;
import com.samuel.model.User;
import com.samuel.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optional = userRepository.findByEmail(username);

		return optional
				.map(s -> AuthenticationRequest.builder().email(s.getEmail()).password(s.getPassword())
						.role(s.getRole()).build())
				.orElseThrow(() -> new UsernameNotFoundException("UserName not found"));

	}

}
