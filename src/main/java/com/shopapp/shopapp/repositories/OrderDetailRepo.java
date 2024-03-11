package com.shopapp.shopapp.repositories;

import com.shopapp.shopapp.model.Category;
import com.shopapp.shopapp.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<OrderDetails,Long> {
    List<OrderDetails> findByOrdersId(Long orderId);
}
