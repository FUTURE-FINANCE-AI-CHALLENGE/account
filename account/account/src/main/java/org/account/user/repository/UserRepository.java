package org.account.user.repository;

import org.account.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User create(User user){
        String sql = "insert into users (id, password, name, phoneNumber, gender, role, email, birthYear) values (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getName(), user.getPhoneNumber(), user.getGender(), user.getRole(), user.getEmail(), user.getBirthYear());
        return user;
    }

}
