package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;

@Component
public class FriendsDao implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    public FriendsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(int id, int friendId) {
        String sql = "insert into user_friends(user_id, friend_id) values (?, ?)";
        System.out.println(sql);
        jdbcTemplate.update(sql, id, friendId);
    }

    @Override
    public void removeFriend(int id, int friendId) {
        String sql = "delete from user_friends where user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sql, id, friendId);
    }
}