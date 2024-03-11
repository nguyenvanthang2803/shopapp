package com.shopapp.shopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tokens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tokens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
    private boolean revoked;
    private boolean expired;
    @ManyToOne
    private User user;
}
