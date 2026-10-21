package com.reddit.reddit_clone.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.*;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.FetchType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {
	@Id
	@GeneratedValue(stategy = IDENTITY)
	private Long id;
	private String token;

	@OneToOne(fetch = LAZY)
	private User user;
	private Instant expiryDate;
}