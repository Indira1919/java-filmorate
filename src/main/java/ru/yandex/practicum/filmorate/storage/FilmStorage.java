package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {
    Collection<Film> getFilms();

    Film addFilm(Film film);

    Film updateFilm(Film film);

    Film removeFilm(int id);

    Film getFilm(int id);
}
