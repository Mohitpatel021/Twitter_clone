package com.samuel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samuel.repository.UserRepository;

@Service
public class UserServiceImpl {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public Long getUserIdByEmail(String email) {
		return userRepository.findByEmail(email).get().getUserId();
	}

}
