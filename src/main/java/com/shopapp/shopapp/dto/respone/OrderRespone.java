package com.shopapp.shopapp.dto.respone;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRespone {
    private Long id;
    private Long userId;
    private String fullname;
    private String phoneNumber;
    private String address;
    private LocalDateTime orderDate;
    private String note;
    private String status;
    private Float totalMoney;

}
