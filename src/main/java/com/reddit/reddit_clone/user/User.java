package com.reddit.reddit_clone.user;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  
  private String username;
  private String password;
  private String email;
  
  private Instant created;
  private boolean enabled;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role = Role.USER;
}








