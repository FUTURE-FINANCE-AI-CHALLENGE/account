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

    public Optional<Account> getAccount(Long id) {  // 수정: String -> Long
        String sql = "SELECT * FROM account WHERE id = ?";  // 수정: userId -> id

        try {
            Account account = jdbcTemplate.queryForObject(sql, new Object[]{id}, new AccountRowMapper());  // 수정: userId -> id
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new IllegalStateException("Multiple accounts found for id: " + id, e);  // 수정: userId -> id
        }
    }

    public void createAccount(Account account) {
        String sql = "INSERT INTO account (userId, title, description, date, amount, type, category) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, account.getUserId(), account.getTitle(), account.getDescription(),
                new java.sql.Date(account.getDate().getTime()), account.getAmount(), account.getType(), account.getCategory());
    }

    public void updateAccount(Account account) {
        String sql = "UPDATE account SET title = ?, description = ?, date = ?, amount = ?, type = ?, category = ? WHERE id = ?";
        jdbcTemplate.update(sql, account.getTitle(), account.getDescription(), new java.sql.Date(account.getDate().getTime()),
                account.getAmount(), account.getType(), account.getCategory(), account.getId());
    }

    public void deleteAccount(Long id) {  // 수정: String -> Long
        String sql = "DELETE FROM account WHERE id = ?";  // 수정: userId -> id
        jdbcTemplate.update(sql, id);
    }

    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            java.util.Date date = rs.getDate("date");  // 수정: java.sql.Date -> java.util.Date
            return Account.builder()
                    .id(rs.getLong("id"))
                    .userId(rs.getString("userId"))
                    .title(rs.getString("title"))
                    .description(rs.getString("description"))
                    .date(date)  // 수정: java.sql.Date -> java.util.Date
                    .amount(rs.getInt("amount"))
                    .type(rs.getString("type"))
                    .category(rs.getString("category"))
                    .build();
        }
    }
}
