package com.data.service;

import com.data.entity.Product;
import com.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll(int page, int size, String keyword) {
        int offset = page * size;
        return productRepository.findAll(offset, size, keyword);
    }

    @Override
    public long count(String keyword) {
        return productRepository.count(keyword);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        productRepository.update(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }
}