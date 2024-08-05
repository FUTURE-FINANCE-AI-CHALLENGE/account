package org.account.service;

import org.account.service.domain.Account;
import org.account.service.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public Optional<Account> getAccount(String userId) {
        return accountRepository.getAccount(userId);
    }

    public void createAccount(Account account) {
        accountRepository.createAccount(account);
    }

    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }

    public void deleteAccount(String userId) {
        accountRepository.deleteAccount(userId);
    }
}
