package com.shopapp.shopapp.repositories;

import com.shopapp.shopapp.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepo extends JpaRepository<Products,Long> {
    boolean existsByName(String name);
    Page<Products> findAll(Pageable pageable);
}
