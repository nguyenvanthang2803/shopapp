package com.shopapp.shopapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String fullname;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    private String note;
    @Column(name = "order_date")
    private Date orderDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "total_money")
    private float totalMoney;
    @Column(name = "shipping_method")
    private String shippingMethod;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "shipping_date")
    private LocalDate shippingDate;
    @Column(name = "payment_method")
    private String paymentMethod;
    private boolean active;
}
