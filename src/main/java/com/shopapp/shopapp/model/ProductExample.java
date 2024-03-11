package com.shopapp.shopapp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="product_example")

public class ProductExample {
    @Id
    private Long id;
    @OneToMany
    private List<ProductImage> productImages;
}
