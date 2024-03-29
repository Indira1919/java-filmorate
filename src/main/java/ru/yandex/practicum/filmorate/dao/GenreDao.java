package ru.yandex.practicum.filmorate.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class GenreDao implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Genre> getGenres() {
        String sql = "select * from genre";
        return jdbcTemplate.query(sql, this::makeGenre);
    }

    public Genre getGenre(int id) {
        String sql = "select * from genre where genre_id = ?";

        List<Genre> genreById = jdbcTemplate.query(sql, this::makeGenre, id);
        if (!genreById.isEmpty()) {
            return genreById.get(0);
        } else {
            return null;
        }
    }

    private Genre makeGenre(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("genre_id");
        String name = resultSet.getString("name");
        return new Genre(id, name);
    }
}