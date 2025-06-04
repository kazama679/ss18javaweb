package com.data.service;

import com.data.entity.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    List<Order> getAll(int page, int size, String username, Date fromDate, Date toDate, String status);
    long count(String username, Date fromDate, Date toDate, String status);
    Order getById(Long id);
    void update(Order order);
}