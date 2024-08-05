package org.account.service;

import org.account.service.domain.Account;
import org.account.service.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(String title, Integer total, Integer income, Integer expense, String category, String description, Date date, String userId) {
        accountRepository.createAccount(title, total, income, expense, category, description, date, userId);
    }

    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }

    public void deleteAccount(String userId) {
        accountRepository.deleteAccount(userId);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public Optional<Account> getAccount(String userId) {
        return accountRepository.getAccount(userId);
    }

    public List<Account> getAccountsByUserId(String userId) {
        return accountRepository.getAccountsByUserId(userId);
    }
}

