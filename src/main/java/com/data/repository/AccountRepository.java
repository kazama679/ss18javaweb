package com.data.repository;

import com.data.entity.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll(int offset, int limit, String keyword);
    long count(String keyword);
    Account findById(Long id);
    void update(Account account);
}