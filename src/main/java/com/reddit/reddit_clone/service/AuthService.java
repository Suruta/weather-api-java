package com.reddit.reddit_clone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reddit.reddit_clone.dto.RegisterRequest;
import com.reddit.reddit_clone.dto.LoginRequest;
import com.reddit.reddit_clone.dto.LoginResponse;
import com.reddit.reddit_clone.exceptions.SpringRedditException;
import com.reddit.reddit_clone.model.NotificationEmail;
import com.reddit.reddit_clone.model.User;
import com.reddit.reddit_clone.model.VerificationToken;
import com.reddit.reddit_clone.repository.UserRepository;
import com.reddit.reddit_clone.repository.VerificationTokenRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);

		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please activate your account",
			user.getEmail(), "Thank you for signing up to Spring Reddit, " +
			"please click on the below url to activate your account: \n" +
			"http://localhost:8080/api/v1/auth/accountVerification/" + token
		));
	}

	public LoginResponse login(LoginRequest loginRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		User user = userRepository.findByUsername(loginRequest.getUsername())
			.orElseThrow(() -> new SpringRedditException("User not found by username"));

		if (!user.isEnabled()) {
			throw new SpringRedditException("User not enabled");
		}

		UserDetails userDetails = UserDetailsImpl.builder().user(user).build();
		String jwtToken = jwtService.generateToken(userDetails);

		return LoginResponse.builder()
						.username(loginRequest.getUsername())
						.token(jwtToken)
						.build();
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepository.save(verificationToken);
		return token;
	}

	@Transactional
	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("Invalid token"));

		fetchUserAndEnable(verificationToken.get());
	}

	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
		user.setEnabled(true);
	}
}








