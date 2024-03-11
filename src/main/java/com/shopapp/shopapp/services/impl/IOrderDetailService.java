package com.shopapp.shopapp.services.impl;

import com.shopapp.shopapp.dto.request.OrderDetailDto;
import com.shopapp.shopapp.exception.DataNotFoundException;
import com.shopapp.shopapp.model.OrderDetails;

import java.util.List;

public interface IOrderDetailService {
    OrderDetails createOrderDetail(OrderDetailDto orderDetailDto) throws DataNotFoundException;
    OrderDetails getOrderDetailById(Long id) throws DataNotFoundException;
    List<OrderDetails> findByOrderId(Long id);
    OrderDetails updateOrderDetail(Long categoryId,OrderDetailDto orderDetailDto);
    void deleteOrderDetail(Long id);
}
