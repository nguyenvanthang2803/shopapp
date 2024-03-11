package com.shopapp.shopapp.repositories;

import com.shopapp.shopapp.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Long> {
    List<Orders> findByUserId(Long userId);
}
