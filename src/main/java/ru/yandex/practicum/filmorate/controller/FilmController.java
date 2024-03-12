package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class FilmController {

    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public Collection<Film> getFilms() {
        return filmService.filmStorage.getFilms();
    }

    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id) {
        return filmService.filmStorage.getFilm(id);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getPopularFilms(count);
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        Film filmAdd = filmService.filmStorage.addFilm(film);
        log.info("Сохраненный фильм: {}", film);

        return filmAdd;
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        Film filmUpdate = filmService.filmStorage.updateFilm(film);
        log.info("Данные обновлены: {}", film);

        return filmUpdate;
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        Film likeAdd = filmService.addLike(id, userId);
        log.info("Лайк добавлен: {}", likeAdd);

        return likeAdd;
    }

    @DeleteMapping("/films/{id}")
    public Film removeFilm(@PathVariable int id) {
        Film filmDelete = filmService.filmStorage.removeFilm(id);
        log.info("Фильм удалён: {}", filmDelete);

        return filmDelete;
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film removeLike(@PathVariable int id, @PathVariable int userId) {
        Film likeRemove = filmService.removeLike(id, userId);
        log.info("Лайк удален: {}", likeRemove);

        return likeRemove;
    }

}
