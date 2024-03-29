package ru.yandex.practicum.filmorate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(UserDbStorage.class);


    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<User> getUsers() {
        String sql = "select * from users";

        return jdbcTemplate.query(sql, this::makeUser);
    }

    @Override
    public User getUser(int id) {
        String sql = "select * from users where user_id = ?";

        List<User> userById = jdbcTemplate.query(sql, this::makeUser, id);
        if (!userById.isEmpty()) {
            return userById.get(0);
        } else {
            return null;
        }
    }

    @Override
    public User addUser(User user) {
        String sql = "insert into users(user_name, email, login, birthday) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getLogin());
            stmt.setDate(4, java.sql.Date.valueOf(user.getBirthday()));

            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sql = "update users set " +
                "user_name = ?, email = ?, login = ? , birthday = ? " +
                "where user_id = ?";
        jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public void removeUser(int id) {
        String sql = "delete from users where user_id = ?";
        if (jdbcTemplate.update(sql, id) > 0) {
            log.info("Пользователь удален: {}", id);
        }
    }

    @Override
    public List<User> getAllFriends(int id) {
        String sql = "SELECT * FROM users WHERE user_id IN (SELECT friend_id FROM user_friends WHERE user_id = ?)";


        return jdbcTemplate.query(sql, this::makeUser, id);
    }

    @Override
    public List<User> getListOfCommonFriends(int id, int otherId) {
        String sql = "select u.user_id, u.email, u.login, u.user_name, u.birthday from user_friends AS uf1 " +
                "INNER JOIN user_friends AS uf2 ON uf1.friend_id = uf2.friend_id " +
                "LEFT OUTER JOIN users AS u ON uf1.friend_id = u.user_id where uf1.user_id = ? and uf2.user_id = ?";

        return jdbcTemplate.query(sql, this::makeUser, id, otherId);
    }

    User makeUser(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String login = resultSet.getString("login");
        String name = resultSet.getString("user_name");
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();

        return new User(id, email, login, name, birthday);
    }
}
