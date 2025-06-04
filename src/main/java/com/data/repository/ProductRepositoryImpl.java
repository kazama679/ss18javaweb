package com.data.repository;

import com.data.entity.Product;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAll(int offset, int limit, String keyword) {
        String jpql = "from Product p where p.productName like :kw";
        return entityManager.createQuery(jpql, Product.class)
                .setParameter("kw", "%" + (keyword == null ? "" : keyword) + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long count(String keyword) {
        String jpql = "select count(p) from Product p where p.productName like :kw";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("kw", "%" + (keyword == null ? "" : keyword) + "%")
                .getSingleResult();
    }

    @Override
    public Product findById(Long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public void delete(Long id) {
        Product p = entityManager.find(Product.class, id);
        if (p != null) entityManager.remove(p);
    }
}