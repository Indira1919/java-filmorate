package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {
    HashMap<Integer, Film> films = new HashMap<>();
    private final LocalDate birthdayOfMovie = LocalDate.of(1895, 12, 28);
    int id = 0;

    public int createID() {
        return ++id;
    }

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getDuration() <= 0 ||
                film.getReleaseDate().isBefore(birthdayOfMovie)) {
            throw new ValidationException("Неправильно введены данные");
        }
        film.setId(createID());
        films.put(film.getId(), film);
        log.info("Сохраненный фильм: {}", film);

        return film;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getName().isBlank() || film.getDescription().length() > 200 || film.getDuration() <= 0 ||
                film.getReleaseDate().isBefore(birthdayOfMovie)) {
            throw new ValidationException("Неправильно введены данные");
        }

        if (!(films.containsKey(film.getId()))) {
            throw new ValidationException("Фильм не найден");
        }

        films.put(film.getId(), film);
        log.info("Данные обновлены: {}", film);

        return film;
    }

}
