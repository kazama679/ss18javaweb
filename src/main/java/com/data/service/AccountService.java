package com.data.service;

import com.data.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAll(int page, int size, String keyword);
    long count(String keyword);
    Account getById(Long id);
    void update(Account account);
}