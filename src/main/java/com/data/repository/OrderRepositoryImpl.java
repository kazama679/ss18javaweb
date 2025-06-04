package com.data.repository;

import com.data.entity.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAll(int offset, int limit, String username, Date fromDate, Date toDate, String status) {
        StringBuilder jpql = new StringBuilder("from Order o where 1=1 ");

        if (username != null && !username.isEmpty()) {
            jpql.append("and o.user.username like :username ");
        }
        if (fromDate != null) {
            jpql.append("and o.orderDate >= :fromDate ");
        }
        if (toDate != null) {
            jpql.append("and o.orderDate <= :toDate ");
        }
        if (status != null && !status.isEmpty()) {
            jpql.append("and o.status = :status ");
        }

        TypedQuery<Order> query = entityManager.createQuery(jpql.toString(), Order.class);

        if (username != null && !username.isEmpty()) {
            query.setParameter("username", "%" + username + "%");
        }
        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }
        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public long count(String username, Date fromDate, Date toDate, String status) {
        StringBuilder jpql = new StringBuilder("select count(o) from Order o where 1=1 ");

        if (username != null && !username.isEmpty()) {
            jpql.append("and o.user.username like :username ");
        }
        if (fromDate != null) {
            jpql.append("and o.orderDate >= :fromDate ");
        }
        if (toDate != null) {
            jpql.append("and o.orderDate <= :toDate ");
        }
        if (status != null && !status.isEmpty()) {
            jpql.append("and o.status = :status ");
        }

        TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);

        if (username != null && !username.isEmpty()) {
            query.setParameter("username", "%" + username + "%");
        }
        if (fromDate != null) {
            query.setParameter("fromDate", fromDate);
        }
        if (toDate != null) {
            query.setParameter("toDate", toDate);
        }
        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }

        return query.getSingleResult();
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void update(Order order) {
        entityManager.merge(order);
    }
}