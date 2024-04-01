package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Collection<Film> getFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void removeFilm(int id);

    Film getFilm(int id);

    List<Genre> getGenres(int filmId);

    void addGenre(int filmId, int genreId);

    void deleteGenres(int filmId);

    List<Film> getPopularFilms(int count);
}
