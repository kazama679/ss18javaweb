package com.data.service;

import com.data.entity.Account;
import com.data.repository.AccountRepository;
import com.data.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll(int page, int size, String keyword) {
        int offset = page * size;
        return accountRepository.findAll(offset, size, keyword);
    }

    @Override
    public long count(String keyword) {
        return accountRepository.count(keyword);
    }

    @Override
    public Account getById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public void update(Account account) {
        accountRepository.update(account);
    }
}