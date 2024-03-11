package com.shopapp.shopapp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @JsonProperty("user_id")
    @Min(value = 1,message = "User'sId must be >0")
    private Long userId;
    @JsonProperty("fullname")
    private String fullName;
    private String email;
    @JsonProperty("phone_number")
    @NotBlank(message = "Phone Number is required")
    @Size(min=5,message = "phone number must be >=5")
    private String phoneNumber;
    private String address;
    private String note;
    @JsonProperty("total_money")
    @Min(value = 0,message = "Total money >0")
    private Float totalMoney;
    @JsonProperty("shipping_method")
    private String shippingMethod;
    @JsonProperty("shipping_address")
    private String shippingAdress;
    @JsonProperty("payment_method")
    private String paymentMethod;

}
