package com.data.service;

import com.data.entity.Order;
import com.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAll(int page, int size, String username, Date fromDate, Date toDate, String status) {
        int offset = page * size;
        return orderRepository.findAll(offset, size, username, fromDate, toDate, status);
    }

    @Override
    public long count(String username, Date fromDate, Date toDate, String status) {
        return orderRepository.count(username, fromDate, toDate, status);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void update(Order order) {
        orderRepository.update(order);
    }
}