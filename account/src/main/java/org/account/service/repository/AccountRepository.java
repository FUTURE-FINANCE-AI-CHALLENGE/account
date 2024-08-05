package org.account.service.repository;

import org.account.service.domain.Account;
import org.account.service.domain.Expense;
import org.account.service.domain.Income;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM account";
        return jdbcTemplate.query(sql, new AccountRowMapper());
    }

    public Optional<Account> getAccount(String userId) {
        String accountSql = "SELECT * FROM account WHERE userId = ?";
        String expensesSql = "SELECT * FROM expense WHERE accountId = (SELECT id FROM account WHERE userId = ?)";
        String incomeSql = "SELECT * FROM income WHERE accountId = (SELECT id FROM account WHERE userId = ?)";

        try {
            Account account = jdbcTemplate.queryForObject(accountSql, new Object[]{userId}, new AccountRowMapper());
            Long accountId = account.getId();

            List<Expense> expenses = jdbcTemplate.query(expensesSql, new Object[]{accountId}, new ExpenseRowMapper());
            List<Income> incomes = jdbcTemplate.query(incomeSql, new Object[]{accountId}, new IncomeRowMapper());

            account.setExpenses(expenses);
            account.setIncomes(incomes);

            return Optional.of(account);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalStateException("Multiple accounts found for userId: " + userId, e);
        }
    }

    public void createAccount(Account account) {
        String sql = "INSERT INTO account (title, description, date, userId) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, account.getTitle(), account.getDescription(), account.getDate(), account.getUserId());

        Long accountId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        for (Expense expense : account.getExpenses()) {
            createExpense(expense, accountId);
        }

        for (Income income : account.getIncomes()) {
            createIncome(income, accountId);
        }
    }

    public void updateAccount(Account account) {
        String sql = "UPDATE account SET title = ?, description = ?, date = ? WHERE id = ?";
        jdbcTemplate.update(sql, account.getTitle(), account.getDescription(), new java.sql.Date(account.getDate().getTime()), account.getId());

        deleteExpenses(account.getId());
        deleteIncomes(account.getId());

        for (Expense expense : account.getExpenses()) {
            createExpense(expense, account.getId());
        }

        for (Income income : account.getIncomes()) {
            createIncome(income, account.getId());
        }
    }

    public void deleteAccount(String userId) {
        Long accountId = getAccount(userId).map(Account::getId).orElseThrow();
        String sql = "DELETE FROM account WHERE userId = ?";
        jdbcTemplate.update(sql, userId);

        deleteExpenses(accountId);
        deleteIncomes(accountId);
    }

    private void createExpense(Expense expense, Long accountId) {
        String sql = "INSERT INTO expense (accountId, amount) VALUES (?, ?)";
        jdbcTemplate.update(sql, accountId, expense.getAmount());
    }

    private void createIncome(Income income, Long accountId) {
        String sql = "INSERT INTO income (accountId, amount) VALUES (?, ?)";
        jdbcTemplate.update(sql, accountId, income.getAmount());
    }

    private void deleteExpenses(Long accountId) {
        String sql = "DELETE FROM expense WHERE accountId = ?";
        jdbcTemplate.update(sql, accountId);
    }

    private void deleteIncomes(Long accountId) {
        String sql = "DELETE FROM income WHERE accountId = ?";
        jdbcTemplate.update(sql, accountId);
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            java.util.Date date = rs.getDate("date");
            return Account.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getString("userId"))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .date((Date) date)
                    .build();
        }
    }

    private static class ExpenseRowMapper implements RowMapper<Expense> {
        @Override
        public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Expense.builder()
                    .id(rs.getLong("id"))
                    .accountId(rs.getLong("accountId"))
                    .amount(rs.getInt("amount"))
                    .build();
        }
    }

    private static class IncomeRowMapper implements RowMapper<Income> {
        @Override
        public Income mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Income.builder()
                    .id(rs.getLong("id"))
                    .accountId(rs.getLong("accountId"))
                    .amount(rs.getInt("amount"))
                    .build();
        }
    }
}
