package com.data.repository;

import com.data.entity.Order;
import java.util.Date;
import java.util.List;

public interface OrderRepository {
    List<Order> findAll(int offset, int limit, String username, Date fromDate, Date toDate, String status);
    long count(String username, Date fromDate, Date toDate, String status);
    Order findById(Long id);
    void update(Order order);
}