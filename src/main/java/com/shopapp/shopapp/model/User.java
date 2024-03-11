package com.shopapp.shopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "t_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntityCreatUpDate implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullname;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    private String password;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "is_active")
    private boolean active=true;
    @Column(name = "facebook_account_id")
    private int facebookAccountId;
    @Column(name = "google_account_id")
    private int googleAccountId;
    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        //authorityList.add(new SimpleGrantedAuthority("ROLE_"+getRole().getName()));
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
