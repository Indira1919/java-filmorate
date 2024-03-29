package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;


import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmServiceTest {
    FilmService filmService = new FilmService();

    @ParameterizedTest
    @MethodSource("provideArgsForAddFilm")
    void shouldAddFilm(String name, String description, int duration, LocalDate releaseDate) {
        Film film = new Film();
        film.setName(name);
        film.setDescription(description);
        film.setDuration(duration);
        film.setReleaseDate(releaseDate);

        assertThrows(ValidationException.class, () -> filmService.addFilm(film));
    }

    private static Stream<Arguments> provideArgsForAddFilm() {
        return Stream.of(
                Arguments.of("Терминатор", "Восстание машин", 60,
                        LocalDate.of(1894, 10, 26)),
                Arguments.of("       ", "«Молния» Маккуин", 90,
                        LocalDate.of(2006, 6, 15)),
                Arguments.of("Константин: Повелитель тьмы", "Побывать в аду, но и вернуться обратно", 0,
                        LocalDate.of(2005, 3, 24)),
                Arguments.of("Дом у озера", "Зима 2006 года. Ощутив необходимость изменить свою жизнь, " +
                                "доктор Кейт Форестер покидает свой необычный дом у озера в пригороде Иллинойса и " +
                                "устраивается на работу в чикагскую больницу. Единственное, о чем жалеет Кейт, так " +
                                "это о своем красивом доме с " +
                                "огромными окнами.", 120,
                        LocalDate.of(2006, 6, 16))
        );
    }
}
