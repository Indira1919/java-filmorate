package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    public FilmStorage filmStorage;
    public UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addLike(int id, int userId) {
        if (filmStorage.getFilm(id) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        if (userStorage.getUser(userId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        filmStorage.getFilm(id).getLikes().add(userId);
        return filmStorage.updateFilm(filmStorage.getFilm(id));
    }

    public Film removeLike(int id, int userId) {
        if (filmStorage.getFilm(id) == null) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        if (userStorage.getUser(userId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        filmStorage.getFilm(id).getLikes().remove(userId);
        return filmStorage.updateFilm(filmStorage.getFilm(id));
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted((i1, i2) -> Integer.compare(i2.getLikes().size(), i1.getLikes().size()))
                .limit(count).collect(Collectors.toList());
    }
}
