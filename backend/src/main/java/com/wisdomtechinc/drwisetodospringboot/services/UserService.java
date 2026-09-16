package com.wisdomtechinc.drwisetodospringboot.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wisdomtechinc.drwisetodospringboot.exceptions.InvalidCredentialsException;
import com.wisdomtechinc.drwisetodospringboot.exceptions.UsernameAlreadyExistsException;
import com.wisdomtechinc.drwisetodospringboot.models.AppUser;
import com.wisdomtechinc.drwisetodospringboot.repositories.UserRepository;

@Service
public class UserService {

	private final JwtService jwtService;

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}

	public AppUser register(String username, String password) {
		Optional<AppUser> foundUser = userRepository.findByUsername(username);
		if (foundUser.isPresent()) {
			// Throw username already exist exception
			throw new UsernameAlreadyExistsException(username);
		}

		// Hash the password
		String hashedPassword = passwordEncoder.encode(password);

		AppUser user = new AppUser(username, hashedPassword);
		return userRepository.save(user);
	}

	public String login(String username, String password) {
		AppUser user = userRepository.findByUsername(username) //
			.orElseThrow(InvalidCredentialsException::new);

		if (!passwordEncoder.matches(password, user.getPasswordHash())) {
			throw new InvalidCredentialsException();
		}

		return jwtService.generateToken(username);
	}

}
