package com.shopapp.shopapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Products extends BaseEntityCreatUpDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Min(value = 0,message = "Price must be greater than 0")
    private float price;
    private String thumbnail;
    private String description;
    @ManyToOne
    private Category category;

}
