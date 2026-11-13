package com.reddit.reddit_clone.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.reddit.reddit_clone.exceptions.SpringRedditException;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.service.UserDetailsImpl;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ApplicationConfig {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Bean
	UserDetailsService userDetails() {
		return username -> {
			Optional<User> user = userRepository.findByUsername(username);
			user.orElseThrow(() -> new SpringRedditException("Username does not exist"));
			return new UserDetailsImpl(user.get());
		};
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetails());
		authProvider.setPasswordEncoder(passwordEncoder);
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
















