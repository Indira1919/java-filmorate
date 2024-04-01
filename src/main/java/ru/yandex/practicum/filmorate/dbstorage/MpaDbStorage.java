package ru.yandex.practicum.filmorate.dbstorage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    public MpaDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Mpa> getAllMpa() {
        String sql = "select * from mpa";
        return jdbcTemplate.query(sql, this::makeMpa);
    }

    @Override
    public Mpa getMpa(int id) {
        String sql = "select * from mpa where mpa_id = ?";

        List<Mpa> mpaById = jdbcTemplate.query(sql, this::makeMpa, id);
        if (!mpaById.isEmpty()) {
            return mpaById.get(0);
        } else {
            return null;
        }
    }

    private Mpa makeMpa(ResultSet resultSet, int rowNum) throws SQLException {
        int id = resultSet.getInt("mpa_id");
        String name = resultSet.getString("name");

        return new Mpa(id, name);
    }
}