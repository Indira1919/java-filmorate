package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmService {

    public FilmStorage filmStorage;
    public LikesStorage likesStorage;
    public GenreStorage genreStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, LikesStorage likesStorage, GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.likesStorage = likesStorage;
        this.genreStorage = genreStorage;
    }

    public FilmService() {

    }

    public Film addFilm(Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getDuration() <= 0
                || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getMpa().getId() > 5) {
            throw new ValidationException("Неправильно введены данные");
        }

        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        if (filmStorage.getFilm(film.getId()) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getDuration() <= 0
                || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getMpa().getId() > 5) {
            throw new ValidationException("Неправильно введены данные");
        }

        return filmStorage.updateFilm(film);
    }

    public Film getFilm(int id) {
        if (filmStorage.getFilm(id) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        return filmStorage.getFilm(id);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getPopularFilms(count);
    }

    public void addLike(int id, int userId) {
        if (filmStorage.getFilm(id) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        likesStorage.addLike(id, userId);
    }

    public void removeLike(int id, int userId) {
        if (filmStorage.getFilm(id) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        likesStorage.removeLike(id, userId);
    }

    public void removeFilm(int id) {
        if (filmStorage.getFilm(id) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        filmStorage.removeFilm(id);
    }
}
