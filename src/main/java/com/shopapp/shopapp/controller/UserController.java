package com.shopapp.shopapp.controller;

import com.shopapp.shopapp.dto.request.UserLoginDTO;
import com.shopapp.shopapp.dto.request.UserRegisterDTO;
import com.shopapp.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegisterDTO userDTO
            , BindingResult result){
        try {
        if(result.hasErrors()){
            List<String> errorMessage =result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PasswordNotMatch");
        }
        userService.createUser(userDTO);

            return ResponseEntity.ok("Register Sucessfull");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserLoginDTO userDTO){
        try {
            String token = userService.login(userDTO.getPhoneNumber(),userDTO.getPassword());
            return ResponseEntity.ok(token);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
