package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    UserController userController = new UserController();

    @Test
    void shouldAddUser() {
        User user = new User();
        user.setEmail("friend.ru");
        user.setLogin("friend");
        user.setBirthday(LocalDate.of(1996, 8, 19));

        User user1 = new User();
        user1.setEmail("test@yandex.ru");
        user1.setLogin("test");
        user1.setBirthday(LocalDate.of(2027, 8, 19));

        User user2 = new User();
        user2.setEmail("user@yandex.ru");
        user2.setLogin("best user");
        user2.setBirthday(LocalDate.of(1996, 7, 16));


        assertThrows(ValidationException.class, () -> userController.addUser(user));
        assertThrows(ValidationException.class, () -> userController.addUser(user1));
        assertThrows(ValidationException.class, () -> userController.addUser(user2));
    }

}
