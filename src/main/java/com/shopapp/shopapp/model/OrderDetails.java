package com.shopapp.shopapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Orders orders;
    @ManyToOne
    private Products products;
    @Min(value = 0,message = "Price must be greated than 0")
    private float price;
    @Column(name = "number_of_products")
    @Min(value = 0)
    private int NumberOfProducts;
    @Min(value = 0,message = "totalMoney must be greated than 0")
    @Column(name = "total_money")
    private float totalMoney;
    private String color;

}
