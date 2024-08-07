package org.account.service;

import org.account.service.domain.Account;
import org.account.service.repository.AccountRepository;
import org.springframework.stereotype.Service;

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

    public Optional<Account> getAccount(Long id) {  // 수정: String -> Long
        return accountRepository.getAccount(id);
    }

    public void createAccount(Account account) {
        accountRepository.createAccount(account);
    }

    public void updateAccount(Account account) {
        accountRepository.updateAccount(account);
    }

    public void deleteAccount(Long id) {  // 수정: String -> Long
        accountRepository.deleteAccount(id);
    }
}
