package org.account.service.controller;

import lombok.RequiredArgsConstructor;
import org.account.service.AccountService;
import org.account.service.domain.Account;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    @PostMapping
    public void createAccount(@RequestBody Account account) {
        accountService.createAccount(
                account.getTitle(),
                account.getTotal(),
                account.getIncome(),
                account.getExpense(),
                account.getCategory(),
                account.getDescription(),
                new Date(account.getDate().getTime()),
                account.getUserId()
        );
    }

    @PutMapping
    public void updateAccount(@RequestBody Account account) {
        accountService.updateAccount(account);
    }


    @DeleteMapping("/{userId}")
    public void deleteAccount(@PathVariable String userId) {
        accountService.deleteAccount(userId);
    }


    @GetMapping("/{userId}")
    public Optional<Account> getAccountById(@PathVariable String userId) {
        return accountService.getAccount(userId);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }


}
