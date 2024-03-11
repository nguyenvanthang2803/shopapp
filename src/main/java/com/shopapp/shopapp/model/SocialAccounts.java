package com.shopapp.shopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Stack;

@Entity
@Table(name = "social_accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialAccounts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String provider;
    @Column(name = "provider_id")
    private String providerId;
    private String email;
    private String name;
    @ManyToOne
    private User user;
}
