package com.data.repository;

import com.data.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Account> findAll(int offset, int limit, String keyword) {
        String jpql = "from Account a where a.username like :kw or a.email like :kw";
        return entityManager.createQuery(jpql, Account.class)
                .setParameter("kw", "%" + (keyword == null ? "" : keyword) + "%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public long count(String keyword) {
        String jpql = "select count(a) from Account a where a.username like :kw or a.email like :kw";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("kw", "%" + (keyword == null ? "" : keyword) + "%")
                .getSingleResult();
    }

    @Override
    public Account findById(Long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public void update(Account account) {
        entityManager.merge(account);
    }
}