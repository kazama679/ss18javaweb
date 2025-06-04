package com.data.service;

import com.data.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll(int page, int size, String keyword);
    long count(String keyword);
    Product getById(Long id);
    void save(Product product);
    void update(Product product);
    void delete(Long id);
}