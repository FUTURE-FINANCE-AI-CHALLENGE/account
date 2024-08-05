package org.account.user.repository;

import org.account.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public User create(User user) {
        try {
            String sql = "INSERT INTO users (userId, password, name, phoneNumber, gender, role, email, birthYear) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getPhoneNumber(), user.getGender(), user.getRole(), user.getEmail(), user.getBirthYear());
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e); // 예외를 다시 던짐
        }
    }

    @Transactional
    public User update(User user) {
        String sql = "UPDATE users SET password=?, name=?, phoneNumber=?, gender=?, role=?, email=?, birthYear=? WHERE userId=?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getPhoneNumber(), user.getGender(), user.getRole(), user.getEmail(), user.getBirthYear(), user.getUserId());
        return user;
    }

    @Transactional
    public int delete(String userId) {
        String sql = "DELETE FROM users WHERE userId=?";
        return jdbcTemplate.update(sql, userId);
    }

    public List<User> getList() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    public Optional<User> get(String userId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM users WHERE userId = ?", new Object[]{userId}, new UserRowMapper())
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getLong("id")) // id 필드 매핑
                    .userId(rs.getString("userId"))
                    .password(rs.getString("password"))
                    .name(rs.getString("name"))
                    .phoneNumber(rs.getString("phoneNumber"))
                    .gender(rs.getString("gender"))
                    .role(rs.getString("role"))
                    .email(rs.getString("email"))
                    .birthYear(rs.getString("birthYear"))
                    .build();
        }
    }

    public Optional<User> findByUserIdAndPassword(String userId, String password) {
        String sql = "SELECT * FROM users WHERE userId = ? AND password = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{userId, password}, new UserRowMapper()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}