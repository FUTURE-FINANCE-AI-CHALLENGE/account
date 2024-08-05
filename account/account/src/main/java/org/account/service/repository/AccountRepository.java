package org.account.service.repository;

import org.account.service.domain.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;
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
    public List<Account> getAccountsByUserId(String userId) {
        String sql = "SELECT * FROM account WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new AccountRowMapper());
    }
    public void createAccount(String title, Integer total, Integer income, Integer expense, String category, String description, Date date, String userId) {
        String sql = "INSERT INTO account (title, total, income, expense, category, description, date, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, title, total, income, expense, category, description, date, userId);
    }

    public void updateAccount(Account account) {
        String sql = "UPDATE account SET title = ?, total = ?, income = ?, expense = ?, category = ?, description = ?, date = ? WHERE userId = ?";
        jdbcTemplate.update(sql,
                account.getTitle(),
                account.getTotal(),
                account.getIncome(),
                account.getExpense(),
                account.getCategory(),
                account.getDescription(),
                new java.sql.Date(account.getDate().getTime()),
                account.getUserId()
        );
    }

    public void deleteAccount(String userId) {
        String sql = "DELETE FROM account WHERE userId = ?";
        jdbcTemplate.update(sql, userId);
    }

    public Optional<Account> getAccount(String userId) {
        String sql = "SELECT * FROM account WHERE userId = ?";
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(sql, new Object[]{userId}, new AccountRowMapper())
            );
        } catch (EmptyResultDataAccessException e) {
            // 결과가 없는 경우
            return Optional.empty();
        } catch (IncorrectResultSizeDataAccessException e) {
            // 여러 레코드가 반환된 경우
            throw new IllegalStateException("Multiple accounts found for userId: " + userId, e);
        }
    }
    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            // ResultSet에서 java.sql.Date로 날짜를 읽어 java.util.Date로 변환
            java.util.Date date = rs.getDate("date");
            return Account.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getString("userId"))
                    .title(rs.getString("title"))
                    .total(rs.getInt("total"))
                    .income(rs.getInt("income"))
                    .expense(rs.getInt("expense"))
                    .category(rs.getString("category"))
                    .description(rs.getString("description"))
                    .date((Date) date) // java.util.Date를 설정
                    .build();
        }
    }
}
