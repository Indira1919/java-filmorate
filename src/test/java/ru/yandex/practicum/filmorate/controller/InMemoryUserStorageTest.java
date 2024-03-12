package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InMemoryUserStorageTest {
    InMemoryUserStorage inMemoryUserStorage = new InMemoryUserStorage();

    @ParameterizedTest
    @MethodSource("provideArgsForAddUser")
    void shouldAddUser(String email, String login, LocalDate birthday) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setBirthday(birthday);

        assertThrows(ValidationException.class, () -> inMemoryUserStorage.addUser(user));
    }

    private static Stream<Arguments> provideArgsForAddUser() {
        return Stream.of(
                Arguments.of("friend.ru", "friend", LocalDate.of(1996, 8, 19)),
                Arguments.of("test@yandex.ru", "test", LocalDate.of(2027, 8, 19)),
                Arguments.of("user@yandex.ru", "best user", LocalDate.of(1996, 7, 16))
        );
    }

}
