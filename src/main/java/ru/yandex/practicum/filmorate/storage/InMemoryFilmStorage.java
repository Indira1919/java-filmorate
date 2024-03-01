package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    HashMap<Integer, Film> films = new HashMap<>();
    private static final LocalDate BIRTHDAY_OF_MOVIE_DATE = LocalDate.of(1895, 12, 28);
    private int id = 0;

    private int createID() {
        return ++id;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film getFilm(int id) {
        if (!(films.containsKey(id))) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        return films.get(id);
    }

    @Override
    public Film addFilm(Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getDuration() <= 0
                || film.getReleaseDate().isBefore(BIRTHDAY_OF_MOVIE_DATE)) {
            throw new ValidationException("Неправильно введены данные");
        }
        film.setId(createID());
        films.put(film.getId(), film);


        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getDuration() <= 0
                || film.getReleaseDate().isBefore(BIRTHDAY_OF_MOVIE_DATE)) {
            throw new ValidationException("Неправильно введены данные");
        }

        if (!(films.containsKey(film.getId()))) {
            throw new FilmNotFoundException("Фильм не найден");
        }

        films.put(film.getId(), film);

        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        if (!(films.containsKey(film.getId()))) {
            throw new FilmNotFoundException("Фильм не найден");
        }
        Film filmDelete = films.get(film.getId());
        films.remove(film.getId());

        return filmDelete;
    }
}
