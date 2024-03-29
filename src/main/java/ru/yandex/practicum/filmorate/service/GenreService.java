package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.GenreNotFoundExceptoin;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;


import java.util.Collection;

@Service
public class GenreService {

    GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Collection<Genre> getGenres() {
        return genreStorage.getGenres();
    }

    public Genre getGenre(int id) {
        if (genreStorage.getGenre(id) != null) {
            return genreStorage.getGenre(id);
        } else {
            throw new GenreNotFoundExceptoin("Жанр не найден");
        }
    }
}