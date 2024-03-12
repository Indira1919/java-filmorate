package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage {

    private int id = 0;
    HashMap<Integer, User> users = new HashMap<>();

    private int createIdUser() {
        return ++id;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public User getUser(int id) {
        if (!(users.containsKey(id))) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        return users.get(id);
    }

    @Override
    public User addUser(User user) {
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@")) || user.getLogin().contains(" ") ||
                user.getLogin().isBlank() || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Неправильный формат данных");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user.setId(createIdUser());
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@")) ||
                user.getBirthday().isAfter(LocalDate.now()) ||
                user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Неправильно введены данные");
        }

        if (!(users.containsKey(user.getId()))) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.put(user.getId(), user);

        return user;
    }

    @Override
    public User removeUser(int id) {
        if (!(users.containsKey(id))) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        User deleteUser = users.get(id);
        users.remove(id);

        return deleteUser;
    }

}
