package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    public UserStorage userStorage;
    FriendsStorage friendsStorage;

    @Autowired
    public UserService(UserStorage userStorage, FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public UserService() {

    }

    public User addUser(User user) {
        if (user.getEmail().isBlank() || !(user.getEmail().contains("@")) || user.getLogin().contains(" ") ||
                user.getLogin().isBlank() || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Неправильный формат данных");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        if (userStorage.getUser(user.getId()) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (user.getEmail().isBlank() || !(user.getEmail().contains("@")) || user.getLogin().contains(" ") ||
                user.getLogin().isBlank() || user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Неправильный формат данных");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return userStorage.updateUser(user);
    }

    public User getUserById(int id) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        return userStorage.getUser(id);
    }

    public List<User> getAllFriends(int id) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        return userStorage.getAllFriends(id);
    }

    public List<User> getListOfCommonFriends(int id, int otherId) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (userStorage.getUser(otherId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        return userStorage.getListOfCommonFriends(id, otherId);
    }

    public void addFriend(int id, int friendId) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (userStorage.getUser(friendId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        friendsStorage.addFriend(id, friendId);
    }

    public void removeFriend(int id, int friendId) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        if (userStorage.getUser(friendId) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        friendsStorage.removeFriend(id, friendId);
    }

    public void removeUser(int id) {
        if (userStorage.getUser(id) == null) {
            throw new UserNotFoundException("Пользователь не найден");
        }

        userStorage.removeUser(id);
    }
}
