package com.data.repository;

import com.data.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll(int offset, int limit, String keyword);
    long count(String keyword);
    Product findById(Long id);
    void save(Product product);
    void update(Product product);
    void delete(Long id);
}