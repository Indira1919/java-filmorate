package ru.yandex.practicum.filmorate.dbstorage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmDbStorageTest {
    private final FilmStorage filmStorage;
    private Mpa mpa;
    private List<Genre> genre;
    private Film newFilm;

    @BeforeEach
    public void createFilm() {
        mpa = new Mpa(2, "PG");
        genre = new ArrayList<>();
        genre.add(new Genre(1, "Comedy"));
        newFilm = new Film(1, "test", "test", LocalDate.of(1990, 1, 1),
                60, mpa, genre);
        filmStorage.addFilm(newFilm);
        newFilm.setId(1);
    }

    @Test
    public void testAddAndFindFilmById() {
        Film film = filmStorage.getFilm(1);

        assertThat(film)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testRemoveFilmById() {
        Film film = filmStorage.getFilm(1);

        assertThat(film)
                .isNotNull();

        filmStorage.removeFilm(newFilm.getId());

        assertThat(filmStorage.getFilm(1))
                .isNull();
    }

    @Test
    public void testGetGenreFilm() {
        assertThat(newFilm.getGenres())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(filmStorage.getGenres(newFilm.getId()));
    }

    @Test
    public void testGetFilms() {
        mpa = new Mpa(1, "G");
        genre = new ArrayList<>();
        genre.add(new Genre(1, "Comedy"));
        Film newFilm2 = new Film(2, "tessst", "tesssst",
                LocalDate.of(1990, 1, 1), 80, mpa, genre);
        filmStorage.addFilm(newFilm2);
        newFilm2.setId(2);
        List<Film> films = new ArrayList<>();
        films.add(newFilm);
        films.add(newFilm2);

        assertThat(films)
                .usingRecursiveComparison()
                .isEqualTo(filmStorage.getFilms());
    }
}
