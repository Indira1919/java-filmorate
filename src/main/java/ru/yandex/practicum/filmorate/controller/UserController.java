package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class UserController {
    private int id = 0;
    HashMap<Integer, User> users = new HashMap<>();

    private int createIdUser() {
        return ++id;
    }

    @GetMapping("/users")
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping("/users")
    public User addUser(@Valid @RequestBody User user) {
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@")) || user.getLogin().contains(" ") ||
                user.getLogin().isBlank() || user.getBirthday().isAfter(LocalDate.now())) {
            log.info("Неправильный формат данных: {}", user);
            throw new ValidationException("Неправильный формат данных");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(createIdUser());
        users.put(user.getId(), user);
        log.info("Сохраненный пользователь: {}", user);

        return user;
    }

    @PutMapping("/users")
    public User updateUser(@Valid @RequestBody User user) {
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@")) ||
                user.getBirthday().isAfter(LocalDate.now()) ||
                user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Неправильно введены данные");
        }
        if (!(users.containsKey(user.getId()))) {
            throw new ValidationException("Пользователь не найден");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);
        log.info("Данные обновлены: {}", user);

        return user;
    }
}
