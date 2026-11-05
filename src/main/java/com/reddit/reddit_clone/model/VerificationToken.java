package com.reddit.reddit_clone.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tokenId;
	private String token;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;
	private Instant expiryDate;
}