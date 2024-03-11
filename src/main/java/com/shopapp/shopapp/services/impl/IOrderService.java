package com.shopapp.shopapp.services.impl;

import com.shopapp.shopapp.dto.request.OrderRequestDto;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.Orders;

import java.util.List;

public interface IOrderService {
    Orders createOrder(OrderRequestDto orderRequestDto) throws DataNotFoundException;
    Orders getOrderById(Long id);
    List<Orders> findByUserId(Long userId);
    Orders updateOrder(Long orderId,OrderRequestDto orderRequestDto) throws DataNotFoundException;
    void deleteOrder(Long id);
}
