package com.shopapp.shopapp.services;

import com.shopapp.shopapp.dto.request.UserRegisterDTO;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.Role;
import com.shopapp.shopapp.model.User;
import com.shopapp.shopapp.repositories.RoleRepo;
import com.shopapp.shopapp.repositories.UserRepo;
import com.shopapp.shopapp.services.impl.IUserService;
import com.shopapp.shopapp.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    @Override
    public User createUser(UserRegisterDTO userRegisterDTO) throws DataNotFoundException {
        String phoneNumber = userRegisterDTO.getPhoneNumber();
        if(userRepo.existsByPhoneNumber(phoneNumber)){
            throw  new IllegalStateException("phone number already exist");
        }
        User newUser = User.builder()
                .fullname(userRegisterDTO.getFullName())
                .phoneNumber(userRegisterDTO.getPhoneNumber())
                .password(userRegisterDTO.getPassword())
                .address(userRegisterDTO.getAddress())
                .dateOfBirth(userRegisterDTO.getDateOfBirth())
                .facebookAccountId(userRegisterDTO.getFacebookAccountId())
                .googleAccountId(userRegisterDTO.getGoogleAccountId()).build();
        Role role = roleRepo.findById(userRegisterDTO.getRoleId()).
                         orElseThrow(()->new DataNotFoundException("role not fold"));
        newUser.setRole(role);
        if(userRegisterDTO.getFacebookAccountId()!=0||userRegisterDTO.getGoogleAccountId()!=0){
            String password = userRegisterDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepo.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        try {
            User existingUser = userRepo.findByPhoneNumber(phoneNumber).get();
           UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phoneNumber,password,existingUser.getAuthorities());
            authenticationManager.authenticate(authenticationToken);
             String token =jwtTokenUtil.generateToken(existingUser);
            return token;
        }catch (Exception e){
            return e.getMessage();
        }

       // String token =jwtTokenUtil.generateToken(existingUser);

    }














}
