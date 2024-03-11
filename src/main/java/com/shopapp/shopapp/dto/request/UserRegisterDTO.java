package com.shopapp.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @JsonProperty("fullname")
    private String fullName;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number  is required")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @JsonProperty("retype_password")
    private String retypePassword;
    @JsonProperty("date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    private int facebookAccountId;
    @JsonProperty("google_account_id")
    private int googleAccountId;
    @NotNull(message = "RoleID is required")
    @JsonProperty("role_id")
    private Long roleId;
}
