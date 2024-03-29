package ru.yandex.practicum.filmorate.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;
    private final Logger log = LoggerFactory.getLogger(FilmDbStorage.class);

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, MpaStorage mpaStorage, GenreStorage genreStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Collection<Film> getFilms() {
        String sql = "select * from film";
        return jdbcTemplate.query(sql, this::makeFilm);
    }

    @Override
    public Film addFilm(Film film) {
        String sql = "insert into film (film_name, description, release_date, duration, mpa_id) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        film.getGenres().stream().distinct().collect(Collectors.toList()).forEach(genre ->
                addGenre(film.getId(), genre.getId()));

        film.setGenres(new ArrayList<>(getGenres(film.getId())));
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "update film set " +
                "film_name = ?, description = ?, release_date = ? , duration = ? , mpa_id = ?" +
                "where film_id = ?";
        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        film.setMpa(mpaStorage.getMpa(film.getMpa().getId()));
        deleteGenres(film.getId());
        film.getGenres().stream().distinct().collect(Collectors.toList()).forEach(genre ->
                addGenre(film.getId(), genre.getId()));

        film.setGenres(new ArrayList<>(getGenres(film.getId())));
        return film;
    }

    @Override
    public void removeFilm(int id) {
        String sql = "delete from film where film_id = ?";
        if (jdbcTemplate.update(sql, id) > 0) {
            log.info("Фильм удален: {}", id);
        }
    }

    @Override
    public Film getFilm(int id) {
        String sql = "select * from film where film_id = ?";

        List<Film> filmById = jdbcTemplate.query(sql, this::makeFilm, id);
        if (!filmById.isEmpty()) {
            return filmById.get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<Genre> getGenres(int filmId) {
        String sql = "select g.genre_id, g.name from film_genres AS fg " +
                "LEFT OUTER JOIN genre AS g ON fg.genre_id = g.genre_id " +
                "where film_id = ? order by g.genre_id ASC";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> genreStorage.getGenre(
                resultSet.getInt("genre_id")), filmId);
    }

    @Override
    public void deleteGenres(int filmId) {
        String sql = "delete from film_genres where film_id = ?";
        jdbcTemplate.update(sql, filmId);
        log.info("Жанры фильма с Id {} удалены", filmId);
    }

    @Override
    public void addGenre(int filmId, int genreId) {
        if (!(genreStorage.getGenres().contains(genreStorage.getGenre(genreId)))) {
            throw new ValidationException("Неправильно введены данные");
        }
        String sql = "insert into film_genres(film_id, genre_id) values (?, ?)";
        jdbcTemplate.update(sql, filmId, genreId);
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        String sql = "select f.* from film AS f " +
                "LEFT OUTER JOIN film_likes AS l ON f.film_id = l.film_id " +
                "group by f.film_id order by count(l.user_id) DESC limit ?";

        return jdbcTemplate.query(sql, this::makeFilm, count);
    }

    Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Integer id = resultSet.getInt("film_id");
        String name = resultSet.getString("film_name");
        String description = resultSet.getString("description");
        LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
        int duration = resultSet.getInt("duration");
        Mpa mpa = mpaStorage.getMpa(resultSet.getInt("mpa_id"));
        Film film = new Film(id, name, description, releaseDate, duration, mpa, null);
        film.setGenres(new ArrayList<>(getGenres(id)));
        return film;
    }
}