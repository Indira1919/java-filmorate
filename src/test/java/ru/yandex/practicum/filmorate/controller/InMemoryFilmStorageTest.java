package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryFilmStorageTest {
    InMemoryFilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();

    @Test
    void shouldAddFilm() {
        Film film1 = new Film();
        film1.setName("Терминатор");
        film1.setDescription("Восстание машин");
        film1.setDuration(60);
        film1.setReleaseDate(LocalDate.of(1894, 10, 26));

        Film film2 = new Film();
        film2.setName("       ");
        film2.setDescription("«Молния» Маккуин");
        film2.setDuration(90);
        film2.setReleaseDate(LocalDate.of(2006, 6, 15));

        Film film3 = new Film();
        film3.setName("Константин: Повелитель тьмы");
        film3.setDescription("Побывать в аду, но и вернуться обратно");
        film3.setDuration(0);
        film3.setReleaseDate(LocalDate.of(2005, 3, 24));

        Film film4 = new Film();
        film4.setName("Дом у озера");
        film4.setDescription("Зима 2006 года. Ощутив необходимость изменить свою жизнь, доктор Кейт Форестер " +
                "покидает свой необычный дом у озера в пригороде Иллинойса и устраивается на работу в чикагскую " +
                "больницу. Единственное, о чем жалеет Кейт, так это о своем красивом доме с огромными окнами.");
        film4.setDuration(120);
        film4.setReleaseDate(LocalDate.of(2006, 6, 16));


        assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilm(film1));
        assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilm(film2));
        assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilm(film3));
        assertThrows(ValidationException.class, () -> inMemoryFilmStorage.addFilm(film4));

    }
}
